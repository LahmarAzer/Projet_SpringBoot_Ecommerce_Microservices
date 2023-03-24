package tn.esprit.claimfacturesservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.claimfacturesservice.Entities.User;

public interface UserRepository extends JpaRepository<User,String> {
    //User findByRole(User user);
}
