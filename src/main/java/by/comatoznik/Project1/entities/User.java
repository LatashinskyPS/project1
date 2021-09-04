package by.comatoznik.Project1.entities;

import by.comatoznik.Project1.repositories.RoleRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    private String password;

    private boolean enabled;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Collection<Role> roles;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private Collection<UserTest> userTests;

    public void addRole(Role role) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        if (!getRoles().contains(role)) {
            roles.add(role);
        }
    }
    public void addUserTest(UserTest usertest) {
        if (userTests == null) {
            userTests = new ArrayList<>();
        }
        if (!getRoles().contains(usertest)) {
            userTests.add(usertest);
        }
    }

    public void deleteRole(String role, RoleRepository roleRepository) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        List<String> userList = getRoles().stream().map(Role::getName).collect(Collectors.toList());
        if (userList.contains(role)) {
            roles.remove(roleRepository.findByName(role));
        }
    }
}
