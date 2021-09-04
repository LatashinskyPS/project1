package by.comatoznik.Project1.repositories;

import by.comatoznik.Project1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User findById(int id);
    User findByUserEmail(String userEmail);
}
