package tn.esprit.forumms.Service;

import tn.esprit.forumms.Entity.LikeComment;

public interface IServiceLike {
    public LikeComment addLike(LikeComment likeComment, String idUser, Long idComment);
    public void Deletelike(Long idLike,String idUser);

}
