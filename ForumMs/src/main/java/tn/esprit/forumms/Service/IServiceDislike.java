package tn.esprit.forumms.Service;


import tn.esprit.forumms.Entity.DislikeComment;

public interface IServiceDislike {

    public DislikeComment addDislike(DislikeComment dislikeComment,String idUser,Long idComment);
    public void deleteDislike(Long idDislike,String idUser);

}
