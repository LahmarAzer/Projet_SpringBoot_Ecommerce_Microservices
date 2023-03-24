package tn.esprit.forumms.Service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.forumms.Entity.CommentPost;
import tn.esprit.forumms.Entity.DislikeComment;
import tn.esprit.forumms.Entity.LikeComment;
import tn.esprit.forumms.Entity.User;
import tn.esprit.forumms.Repository.CommentPostRepository;
import tn.esprit.forumms.Repository.DislikeRepository;
import tn.esprit.forumms.Repository.LikeRepository;
import tn.esprit.forumms.Repository.UserRepository;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class IServiceDislikeImp implements IServiceDislike{
    public final DislikeRepository dislikeRepository;
    public final UserRepository userRepository;
    public final LikeRepository likeRepository;
    public final CommentPostRepository commentPostRepository;
    @Transactional
    @Override
    public DislikeComment addDislike(DislikeComment dislikeComment,String idUser,Long idComment) {
        User user=userRepository.findById(idUser).orElse(null);
        CommentPost commentPost=commentPostRepository.findById(idComment).orElse(null);
        LikeComment likeComment=likeRepository.getLikeCommentByUserAndCommentPost(user,commentPost);
        DislikeComment disliketest=dislikeRepository.getDislikeCommentByUserAndCommentPost(user,commentPost);

        if (commentPost!=null&&user!=null){
            if (disliketest!=null) {
                return null;
            }
            else if (likeComment==null){
                dislikeComment.setCommentPost(commentPost);
                dislikeComment.setUser(user);
                if (commentPost.getNbDisliked()==null){
                    commentPost.setNbDisliked(1L);
                    return dislikeRepository.save(dislikeComment);
                }
                else
                {
                    commentPost.setNbLiked(commentPost.getNbDisliked()+1);
                    return dislikeRepository.save(dislikeComment);
                }
            }  else{
                dislikeComment.setCommentPost(commentPost);
                dislikeComment.setUser(user);
                if (commentPost.getNbDisliked()==null){
                    commentPost.setNbDisliked(1L);
                    commentPost.setNbLiked(commentPost.getNbLiked()-1);
                    return dislikeRepository.save(dislikeComment);
                }
                else {
                    commentPost.setNbDisliked(commentPost.getNbDisliked()+1);
                    commentPost.setNbLiked(commentPost.getNbLiked()-1);
                    likeRepository.deleteById(likeComment.getIdLike());
                    return dislikeRepository.save(dislikeComment);
                }
            }
        }
        else return null;
    }

    @Transactional
    @Override
    public void deleteDislike(Long idDislike,String idUser) {
        DislikeComment dislikeComment=dislikeRepository.findById(idDislike).orElse(null);
        if (dislikeComment!=null&&dislikeComment.getUser().getIdUser().equals(idUser)){
            CommentPost commentPost=dislikeComment.getCommentPost();
            commentPost.setNbDisliked(commentPost.getNbDisliked()-1);
            dislikeRepository.deleteById(idDislike);
        }
    }
}
