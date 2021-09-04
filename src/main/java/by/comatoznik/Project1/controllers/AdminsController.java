package by.comatoznik.Project1.controllers;

import by.comatoznik.Project1.entities.Role;
import by.comatoznik.Project1.entities.User;
import by.comatoznik.Project1.models.UserValidation;
import by.comatoznik.Project1.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin-panel")
public class AdminsController {
    private final UserRepository userRepository;

    public AdminsController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private boolean checkForAdmin(User user) {
        for (Role role : user.getRoles()) {
            if (role.getName().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkForSuperAdmin(Principal principal) {
        boolean check = false;
        for (Role rolePrincipal : userRepository.findByUserName(principal.getName()).getRoles()) {
            if (rolePrincipal.getName().equals("ROLE_SUPERADMIN")) {
                check = true;
                break;
            }
        }
        return check;
    }


    @GetMapping("")
    public String helloAdmin(Principal principal, Model model) {
        User user = userRepository.findByUserName(principal.getName());
        model.addAttribute("user", user);
        return "admin-panel/helloAdmin";
    }

    @GetMapping("/users/{id}")
    public String user(@PathVariable("id") int id, Model model,
                       Principal principal) {
        boolean isSuperAdmin = checkForSuperAdmin(principal);
        model.addAttribute("superAdmin", isSuperAdmin);
        model.addAttribute("user", userRepository.findById(id));
        return "admin-panel/editUserTemplates/user";
    }

    @GetMapping("/users")
    public String index(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin-panel/editUserTemplates/index";
    }

    @GetMapping("/users/{id}/edit")
    public String editPage(@ModelAttribute("userValidation") UserValidation userValidation,
                           @PathVariable("id") int id, Model model,
                           Principal principal) {
        User user = userRepository.findById(id);
        boolean isSuperAdmin = checkForSuperAdmin(principal);
        if (checkForAdmin(user) && !isSuperAdmin) {
            return "admin-panel/accessErrors/onlySuperAdmins";
        }
        model.addAttribute("user", user);
        userValidation.setUserName(user.getUserName());
        userValidation.setUserEmail(user.getUserEmail());
        return "admin-panel/editUserTemplates/edit";
    }

    @PatchMapping("/users/{id}")
    public String edit(@ModelAttribute("userValidation") @Valid UserValidation userValidation,
                       BindingResult bindingResult,
                       BCryptPasswordEncoder encoder,
                       Principal principal,
                       Model model,
                       @PathVariable("id") int id) {
        boolean isSuperAdmin = checkForSuperAdmin(principal);
        User user = userRepository.findById(id);
        model.addAttribute("user", user);
        if (checkForAdmin(user) && !isSuperAdmin) {
            return "admin-panel/accessErrors/onlySuperAdmins";
        }
        if (bindingResult.hasErrors()) {
            return "admin-panel/editUserTemplates/edit";
        }
        user.setUserEmail(userValidation.getUserEmail());
        user.setUserName(userValidation.getUserName());
        user.setPassword(encoder.encode(userValidation.getPassword()));
        userRepository.save(user);
        return "redirect:/admin-panel";
    }
}
