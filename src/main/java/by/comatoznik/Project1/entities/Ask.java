package by.comatoznik.Project1.entities;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "asks")
public class Ask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public Collection<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<Answer> answers) {
        this.answers = answers;
    }

    @NotEmpty
    private String ask;

    @OneToMany
    @JoinColumn(name = "id_ask")
    private Collection<Answer> answers;

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void addAnswer(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<>();
        }
        if (!getAnswers().contains(answer)) {
            answers.add(answer);
        }
    }

    @OneToMany
    @JoinColumn(name = "id_ask")
    private Collection<UserAnswer> userAnswers;

    public Collection<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Collection<UserAnswer> userAnswer) {
        this.userAnswers = userAnswer;
    }

    @Override
    public String toString() {
        return "Ask{" +
                "ask='" + ask + '\'' +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "id_test")
    private Test test;
}
