package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.msstore.Entity.LikeComment;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
}