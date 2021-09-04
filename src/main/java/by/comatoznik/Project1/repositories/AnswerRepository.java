package by.comatoznik.Project1.repositories;

import by.comatoznik.Project1.entities.Answer;
import by.comatoznik.Project1.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer findById(int id);
}
