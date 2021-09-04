package by.comatoznik.Project1.models;

import by.comatoznik.Project1.entities.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserValidation {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotEmpty(message = "Incorrect size!")
    @Size(min = 4, max = 15, message = "Incorrect size!")
    @Pattern(regexp = "[0-9_A-Za-z]*", message = "Use 0-9, \"_\", A-Z,a-z! ")
    private String userName;

    @NotEmpty(message = "Enter email, please.")
    @Email(message = "Enter email, please.")
    private String userEmail;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "Incorrect size!")
    @Pattern(regexp = "[0-9_A-Za-z]*", message = "Use 0-9, \"_\", A-Z,a-z! ")
    @Size(min = 3, max = 15, message = "Incorrect size!")
    private String password;

    public static User createNewUser(UserValidation userValidation) {
        User user = new User();
        user.setUserName(userValidation.userName);
        user.setPassword(userValidation.password);
        user.setUserEmail(userValidation.userEmail);
        return user;
    }
}
