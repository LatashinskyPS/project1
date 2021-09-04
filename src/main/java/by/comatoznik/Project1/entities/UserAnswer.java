package by.comatoznik.Project1.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "user_answers")
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_ask")
    private Ask ask;

    @Override
    public String toString() {
        return "UserAnswer{" +
                "id=" + id +
                ", ask=" + ask +
                ", answer=" + answer +
                ", userTest=" + userTest +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "id_answer")
    public Answer answer;

    @ManyToOne
    @JoinColumn(name = "id_user_test")
    private UserTest userTest;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ask getAsk() {
        return ask;
    }

    public void setAsk(Ask ask) {
        this.ask = ask;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public UserTest getUserTest() {
        return userTest;
    }

    public void setUserTest(UserTest userTest) {
        this.userTest = userTest;
    }

    public UserAnswer() {

    }
}
