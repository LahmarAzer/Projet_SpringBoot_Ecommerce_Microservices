package tn.esprit.forumms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.forumms.Entity.User;


public interface UserRepository extends JpaRepository<User,String> {

}
