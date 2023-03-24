package tn.esprit.forumms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.forumms.Entity.CommentPost;
import tn.esprit.forumms.Entity.DislikeComment;
import tn.esprit.forumms.Entity.User;

public interface DislikeRepository extends JpaRepository<DislikeComment,Long> {
    public DislikeComment getDislikeCommentByUserAndCommentPost(User user, CommentPost commentPost);
}
