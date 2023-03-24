package tn.esprit.forumms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.forumms.Entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}