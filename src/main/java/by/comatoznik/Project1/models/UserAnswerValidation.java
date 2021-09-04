package by.comatoznik.Project1.models;

import javax.validation.constraints.NotEmpty;

public class UserAnswerValidation {
    public int getNumberOfAnswer() {
        return numberOfAnswer;
    }

    public void setNumberOfAnswer(int numberOfAnswer) {
        this.numberOfAnswer = numberOfAnswer;
    }

    public int numberOfAnswer;

    @Override
    public String toString() {
        return "UserAnswerValidation{" +
                "numberOfAnswer=" + numberOfAnswer +
                '}';
    }

    public UserAnswerValidation() {
        numberOfAnswer = 0;
    }
}
