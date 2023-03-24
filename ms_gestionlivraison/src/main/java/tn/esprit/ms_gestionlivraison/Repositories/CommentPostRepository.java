package tn.esprit.ms_gestionlivraison.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ms_gestionlivraison.Entities.CommentPost;

public interface CommentPostRepository extends JpaRepository<CommentPost, Long> {
}