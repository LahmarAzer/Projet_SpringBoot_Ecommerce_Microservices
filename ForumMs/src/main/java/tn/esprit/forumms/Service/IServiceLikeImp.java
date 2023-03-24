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
public class IServiceLikeImp implements IServiceLike{


    public final DislikeRepository dislikeRepository;
    public final UserRepository userRepository;
    public final LikeRepository likeRepository;
    public final CommentPostRepository commentPostRepository;
    @Transactional
    @Override
    public LikeComment addLike(LikeComment likeComment, String idUser, Long idComment) {
        User user=userRepository.findById(idUser).orElse(null);
        CommentPost commentPost=commentPostRepository.findById(idComment).orElse(null);
        DislikeComment dislikeComment=dislikeRepository.getDislikeCommentByUserAndCommentPost(user,commentPost);
        LikeComment liketest=likeRepository.getLikeCommentByUserAndCommentPost(user,commentPost);

        if (commentPost!=null&&user!=null){
            if (liketest!=null) {
                return null;
            }
            else if (dislikeComment==null){
                likeComment.setCommentPost(commentPost);
                likeComment.setUser(user);
                if (commentPost.getNbLiked()==null){
                    commentPost.setNbLiked(1L);
                    return likeRepository.save(likeComment);
                }
                else
                {
                    commentPost.setNbDisliked(commentPost.getNbLiked()+1);
                    return likeRepository.save(likeComment);
                }
            }  else{
                likeComment.setCommentPost(commentPost);
                likeComment.setUser(user);
                if (commentPost.getNbLiked()==null){
                    commentPost.setNbLiked(1L);
                    commentPost.setNbDisliked(commentPost.getNbDisliked()-1);
                    return likeRepository.save(likeComment);
                }
                else {
                    commentPost.setNbLiked(commentPost.getNbLiked()+1);
                    commentPost.setNbDisliked(commentPost.getNbDisliked()-1);
                    dislikeRepository.deleteById(dislikeComment.getIdDislike());
                    return likeRepository.save(likeComment);
                }
            }
        }
        else return null;
    }
    @Transactional
    @Override
    public void Deletelike(Long idLike,String idUser) {
        LikeComment likeComment=likeRepository.findById(idLike).orElse(null);
        if (likeComment!=null&&likeComment.getUser().getIdUser().equals(idUser)){
            CommentPost commentPost=likeComment.getCommentPost();
            commentPost.setNbLiked(commentPost.getNbLiked()-1);
            likeRepository.deleteById(idLike);
        }
    }
}