package by.comatoznik.Project1.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public Collection<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Collection<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    @NotEmpty
    private String answer;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_answer")
    private Collection<UserAnswer> userAnswers;

    @ManyToOne
    @JoinColumn(name = "id_ask")
    private Ask ask;

    @Override
    public String toString() {
        return "Answer{" +
                "answer='" + answer + '\'' +
                '}';
    }

    public Ask getAsk() {
        return ask;
    }

    public void setAsk(Ask ask) {
        this.ask = ask;
    }
}
