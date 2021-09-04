package by.comatoznik.Project1.repositories;

import by.comatoznik.Project1.entities.Ask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AskRepository extends JpaRepository<Ask,Long> {
    Ask findById(int id);
    void deleteById(int id);
}
