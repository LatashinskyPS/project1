package by.comatoznik.Project1.repositories;

import by.comatoznik.Project1.entities.Role;
import by.comatoznik.Project1.entities.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer,Long> {
}
