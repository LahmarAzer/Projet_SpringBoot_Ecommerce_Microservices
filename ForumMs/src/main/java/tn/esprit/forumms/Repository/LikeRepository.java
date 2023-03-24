package tn.esprit.forumms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.forumms.Entity.CommentPost;
import tn.esprit.forumms.Entity.LikeComment;
import tn.esprit.forumms.Entity.User;

public interface LikeRepository extends JpaRepository<LikeComment,Long> {
    public LikeComment getLikeCommentByUserAndCommentPost(User user, CommentPost commentPost);
}
