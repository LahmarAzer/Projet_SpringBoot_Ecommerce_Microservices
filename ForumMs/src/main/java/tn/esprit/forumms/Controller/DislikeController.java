package tn.esprit.forumms.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.forumms.Entity.DislikeComment;
import tn.esprit.forumms.Service.IServiceDislike;

@RestController
@AllArgsConstructor
@RequestMapping("/forum/dislike")
public class DislikeController {

    public final IServiceDislike iServiceDislike;

    @PostMapping("/dislikeComment/{idUser}/{idComment}")
    public DislikeComment addDislike(@RequestBody DislikeComment dislikeComment, @PathVariable String idUser,@PathVariable Long idComment){
        return iServiceDislike.addDislike(dislikeComment,idUser,idComment);
    }

    @DeleteMapping("/deletedislike/{idDislike}/{idUser}")
    public void deleteDislike(@PathVariable Long idDislike,@PathVariable String idUser){
        iServiceDislike.deleteDislike(idDislike,idUser);
    }
}