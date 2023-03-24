package tn.esprit.forumms.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.forumms.Entity.Post;
import tn.esprit.forumms.Service.IPostService;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/forum/post")
public class PostController{
    public final IPostService iPostService;

    @PostMapping("/add")
    public ResponseEntity<?> addPost(@RequestParam("file") MultipartFile file,
                                     @RequestParam("description") String description,
                                     @RequestParam("userId") String userId,
                                     @RequestParam("topic") String topic,
                                     @RequestParam("categoryId") Long categoryId) throws IOException {

        Post post = new Post();
        post.setDescriptionPost(description);
        post.setTopicPost(topic);

        Post savedPost = iPostService.addPost(post, userId, categoryId, file);
        return ResponseEntity.ok(savedPost);
    }

    @GetMapping("getall")
    public List<Post> getAllPosts(){
        return iPostService.getAllPosts();
    }

    @DeleteMapping("delete/{idPost}/{idUser}")
    public void deletePost(@PathVariable Long idPost,@PathVariable String idUser){
        iPostService.deletePost(idPost, idUser);
    }

    @GetMapping("getById/{idPost}")
    public Post getById(@PathVariable Long idPost){
        return iPostService.getById(idPost);
    }

    @PutMapping("edit/{idPost}/{idUser}")
    public Post editPost(@RequestBody Post p,@PathVariable Long idPost,@PathVariable String idUser){
        return iPostService.editPost(p,idPost,idUser);
    }

}
