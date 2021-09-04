package by.comatoznik.Project1.controllers;

import by.comatoznik.Project1.entities.User;
import by.comatoznik.Project1.models.RoleValidation;
import by.comatoznik.Project1.repositories.RoleRepository;
import by.comatoznik.Project1.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/super-admin-panel")
public class SuperAdminsController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public SuperAdminsController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    @GetMapping("/roles/{id}")
    public String roles(@PathVariable("id") int id, Model model) {
        User user = userRepository.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roleValidation",
                RoleValidation.createRoleValidation(user));
        return "admin-panel/editUserTemplates/updateRoles";
    }

    @PatchMapping("/roles/{id}")
    public String updateRoles(@PathVariable("id") int id,
                              @ModelAttribute("roleValidation") @Valid RoleValidation roleValidation) {
        User user = userRepository.findById(id);
        if (roleValidation.getIsAdmin().equals("true")) {
            user.addRole(roleRepository.findByName("ROLE_ADMIN"));
        } else {
            user.deleteRole("ROLE_ADMIN", roleRepository);
        }
        if (roleValidation.getIsUser().equals("true")) {
            user.addRole(roleRepository.findByName("ROLE_USER"));
        } else {
            user.deleteRole("ROLE_USER", roleRepository);
        }
        if (roleValidation.getIsSuperAdmin().equals("true")) {
            user.addRole(roleRepository.findByName("ROLE_SUPERADMIN"));
        } else {
            user.deleteRole("ROLE_SUPERADMIN", roleRepository);
        }
        userRepository.save(user);
        return "redirect:/admin-panel/users/" + id;
    }
}
