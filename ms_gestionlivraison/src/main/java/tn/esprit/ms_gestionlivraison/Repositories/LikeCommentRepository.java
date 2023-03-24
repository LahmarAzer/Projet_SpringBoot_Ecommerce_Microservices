package tn.esprit.ms_gestionlivraison.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ms_gestionlivraison.Entities.LikeComment;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
}