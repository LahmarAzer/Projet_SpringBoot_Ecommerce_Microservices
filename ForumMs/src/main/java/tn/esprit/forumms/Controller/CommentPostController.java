package tn.esprit.forumms.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.forumms.Entity.CommentPost;
import tn.esprit.forumms.Service.ICommentPostService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/forum/comment")
public class CommentPostController {
    public final ICommentPostService iCommentPostService;

    @GetMapping("/hello")
    public String hello(){
        return ("hello Forum");
    }

    @GetMapping("/getall")
    public List<CommentPost> getall(){
        return iCommentPostService.getAllComments();
    }

    @PostMapping("/add/{idUser}/{idPost}")
    public CommentPost addComment(@RequestBody CommentPost c, @PathVariable String idUser,@PathVariable Long idPost){
        return iCommentPostService.addComment(c,idUser,idPost);
    }

    @PutMapping("/edit/{idUser}/{idPost}")
    public CommentPost editComment(@RequestBody CommentPost c, @PathVariable String idUser,@PathVariable Long idPost){
        return iCommentPostService.editComment(c,idUser,idPost);
    }
    @GetMapping("/getById/{idComment}")
    public CommentPost getCommById(@PathVariable Long idComment){
        return iCommentPostService.getById(idComment);
    }

    @DeleteMapping("/delete/{idComment}/{idUser}")
    public void deleteComment(@PathVariable Long idComment,@PathVariable String idUser){
        iCommentPostService.deleteComment(idComment,idUser);
    }

    @PutMapping("/setProduct/{idComment}/{idProduct}")
    public CommentPost setProductToComment(@PathVariable Long idComment,@PathVariable Long idProduct){
        return iCommentPostService.setProductToComment(idComment,idProduct);
    }

    @GetMapping("/getSorted/{idPost}")
    public List<CommentPost> getCommentsSortedByAverage(@PathVariable Long idPost){
        return iCommentPostService.getCommentsSortedByAverage(idPost);
    }

}