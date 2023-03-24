package tn.esprit.forumms.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.forumms.Entity.LikeComment;
import tn.esprit.forumms.Service.IServiceLike;
@RestController
@AllArgsConstructor
@RequestMapping("/forum/like")
public class LikeController {

    public final IServiceLike iServiceLike;


    @PostMapping("/likeComment/{idUser}/{idComment}")
    public LikeComment addLike(@RequestBody LikeComment likeComment, @PathVariable String idUser, @PathVariable Long idComment){
        return iServiceLike.addLike(likeComment,idUser,idComment);
    }
    @DeleteMapping("/deletelike/{idLike}/{idUser}")
    public void Deletelike(@PathVariable Long idLike,@PathVariable String idUser){
        iServiceLike.Deletelike(idLike,idUser);
    }
}
