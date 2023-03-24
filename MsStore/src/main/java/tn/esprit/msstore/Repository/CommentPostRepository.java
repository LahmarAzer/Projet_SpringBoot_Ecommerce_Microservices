package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.msstore.Entity.CommentPost;

public interface CommentPostRepository extends JpaRepository<CommentPost,Long> {
}
