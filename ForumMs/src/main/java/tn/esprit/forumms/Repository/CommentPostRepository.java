package tn.esprit.forumms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.forumms.Entity.CommentPost;

public interface CommentPostRepository extends JpaRepository<CommentPost, Long> {
}