package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.msstore.Entity.User;

public interface UserRepository extends JpaRepository<User,String> {
}
