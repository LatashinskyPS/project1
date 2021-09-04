package by.comatoznik.Project1.repositories;

import by.comatoznik.Project1.entities.Role;
import by.comatoznik.Project1.entities.Test;
import by.comatoznik.Project1.entities.User;
import by.comatoznik.Project1.entities.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTestRepository extends JpaRepository<UserTest, Long> {
    UserTest findById(int id);
    UserTest findByTestAndUser(Test test, User user);
}
