package by.comatoznik.Project1.models;

import by.comatoznik.Project1.entities.Role;
import by.comatoznik.Project1.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class RoleValidation {
    String isUser = "false";
    String isAdmin = "false";
    String isSuperAdmin = "false";

    @Override
    public String toString() {
        return "RoleValidation{" +
                "isUser='" + isUser + '\'' +
                ", isAdmin='" + isAdmin + '\'' +
                ", isSuperAdmin='" + isSuperAdmin + '\'' +
                '}';
    }

    public String getIsUser() {
        return isUser;
    }

    public void setIsUser(String isUser) {
        this.isUser = isUser;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getIsSuperAdmin() {
        return isSuperAdmin;
    }

    public void setIsSuperAdmin(String isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

    public static RoleValidation createRoleValidation(User user) {
        RoleValidation roleValidation = new RoleValidation();
        List<String> userList = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        if (userList.contains("ROLE_ADMIN")) {
            roleValidation.isAdmin = "true";
        }
        if (userList.contains("ROLE_USER")) {
            roleValidation.isUser = "true";
        }
        if (userList.contains("ROLE_SUPERADMIN")) {
            roleValidation.isSuperAdmin = "true";
        }
        return roleValidation;
    }
}
