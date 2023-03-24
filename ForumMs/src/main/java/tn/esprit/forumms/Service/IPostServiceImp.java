package tn.esprit.forumms.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.forumms.Entity.CategoryProduct;
import tn.esprit.forumms.Entity.Post;
import tn.esprit.forumms.Entity.User;
import tn.esprit.forumms.Repository.CategoryProductRepository;
import tn.esprit.forumms.Repository.PostRepository;
import tn.esprit.forumms.Repository.UserRepository;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class IPostServiceImp implements IPostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryProductRepository categoryProductRepository;


    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }


    public boolean areStringsSimilar(String s1, String s2) {
        Set<String> set1 = new HashSet<>(Arrays.asList(s1.toLowerCase().split(" ")));
        Set<String> set2 = new HashSet<>(Arrays.asList(s2.toLowerCase().split(" ")));

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> difference = new HashSet<>(set1);
        difference.addAll(set2);
        difference.removeAll(intersection);

        return ((double)intersection.size() / (double)difference.size()) >= 0.7;
    }
    @Override
    public Post addPost(Post post,String idUser,Long idCategory, MultipartFile imageFile) throws IOException {

        List<Post> posts=postRepository.findAll();
        CategoryProduct categoryProduct=categoryProductRepository.findById(idCategory).orElse(null);
        User u= userRepository.findById(idUser).orElse(null);
        Post similarPost = null;
        for (Post post1:posts) {
            if (areStringsSimilar(post1.getDescriptionPost(),post.getDescriptionPost())){
                similarPost=post1;
            }
        }
        if (similarPost!=null) {
            return similarPost;
        }
        else if (post.getDescriptionPost()==null||post.getTopicPost()==null){
            return null;
        }
        else {
            post.setUserPost(u);
            post.setDateCreationPost(new Date());
            post.setCategoryPost(categoryProduct);
            if (imageFile != null && !imageFile.isEmpty()) {
                post.setImagePost(imageFile.getBytes());
            }

            return postRepository.save(post);
        }
    }


    @Override
    public Post editPost(Post post, Long idPost, String idUser) {
        Post post1=postRepository.findById(idPost).orElse(null);
        if (post1.getUserPost().getIdUser().equals(idUser)){
            post.setDateCreationPost(post1.getDateCreationPost());
            post.setCategoryPost(post1.getCategoryPost());
            post.setUserPost(post1.getUserPost());
            post.setImagePost(post1.getImagePost());
            return postRepository.save(post);
        }
        return null;
    }

    @Override
    public void deletePost(Long postId,String idUser) {
        User user=userRepository.findById(idUser).orElse(null);
        Post post=postRepository.findById(postId).orElse(null);
        if (post!=null&&post.getUserPost().equals(user))
            postRepository.deleteById(postId);
    }

    @Override
    public Post getById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }
}
