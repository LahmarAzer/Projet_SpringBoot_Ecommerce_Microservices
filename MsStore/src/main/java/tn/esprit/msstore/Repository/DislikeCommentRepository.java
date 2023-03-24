package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.msstore.Entity.DislikeComment;

public interface DislikeCommentRepository extends JpaRepository<DislikeComment, Long> {
}