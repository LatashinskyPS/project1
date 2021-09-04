package by.comatoznik.Project1.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "user_tests")
public class UserTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Collection<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Collection<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_test")
    private Collection<UserAnswer> userAnswers;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_test")
    private Test test;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
