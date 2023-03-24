package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.msstore.Entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {
}
