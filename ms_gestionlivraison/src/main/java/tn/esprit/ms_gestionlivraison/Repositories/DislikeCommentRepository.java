package tn.esprit.ms_gestionlivraison.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ms_gestionlivraison.Entities.DislikeComment;

public interface DislikeCommentRepository extends JpaRepository<DislikeComment, Long> {
}