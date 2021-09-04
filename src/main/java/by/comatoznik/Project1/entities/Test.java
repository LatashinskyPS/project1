package by.comatoznik.Project1.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Write something :-)")
    @Column(unique = true)
    private String title;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_test")
    private Collection<Ask> asks;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_test")
    private Collection<UserTest> userTests;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Collection<Ask> getAsks() {
        return asks;
    }

    public void setAsks(Collection<Ask> asks) {
        this.asks = asks;
    }

    public Collection<UserTest> getUserTests() {
        return userTests;
    }

    public void setUserTests(Collection<UserTest> userTests) {
        this.userTests = userTests;
    }

    public void addAsk(Ask ask) {
        if (asks == null) {
            asks = new ArrayList<>();
        }
        if (!getAsks().contains(ask)) {
            asks.add(ask);
        }
    }
}
