package by.comatoznik.Project1.repositories;

import by.comatoznik.Project1.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findById(int id);
    Role findByName(String name);
    <S extends Role> S save(Role role);
}
