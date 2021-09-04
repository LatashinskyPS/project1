package by.comatoznik.Project1.controllers;

import by.comatoznik.Project1.entities.Role;
import by.comatoznik.Project1.entities.User;
import by.comatoznik.Project1.models.UserValidation;
import by.comatoznik.Project1.repositories.RoleRepository;
import by.comatoznik.Project1.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

@Controller
public class UsersController {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String helloPage(Principal principal) {
        if (principal != null) {
            return "redirect:/authenticated";
        }
        return "hello";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("userValidation") UserValidation userValidation) {
        return "new";
    }

    @GetMapping("/authenticated")
    public String mainMenu(Model model,
                           Principal principal) {
        Collection<Role> roles = userRepository.findByUserName(principal.getName()).getRoles();
        boolean isUser = false;
        boolean isAdmin = false;
        if (roles.contains(roleRepository.findByName("ROLE_USER"))) {
            isUser = true;
        }
        if (roles.contains(roleRepository.findByName("ROLE_ADMIN"))) {
            isAdmin = true;
        }
        model.addAttribute("isUser", isUser);
        model.addAttribute("isAdmin", isAdmin);
        return "mainMenu";
    }

    @PostMapping()
    public String create(@ModelAttribute("userValidation") @Valid UserValidation userValidation,
                         BindingResult bindingResult,
                         BCryptPasswordEncoder encoder,
                         Model model) {

        if (bindingResult.hasErrors()) {
            return "new";
        }
        User user = UserValidation.createNewUser(userValidation);
        User userCheck = userRepository.findByUserName(user.getUserName());
        User userCheckEmail = userRepository.findByUserEmail(user.getUserEmail());
        boolean checkUser = userCheck != null;
        boolean checkEmail = userCheckEmail != null;
        if (checkEmail || checkUser) {
            model.addAttribute("userCheck", userCheck);
            model.addAttribute("userCheckEmail", userCheckEmail);
            return "errorNew";
        }
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.addRole(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
        return "redirect:/authenticated";
    }
}
