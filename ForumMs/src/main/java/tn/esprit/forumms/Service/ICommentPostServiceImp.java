package tn.esprit.forumms.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.forumms.Entity.*;
import tn.esprit.forumms.Repository.CommentPostRepository;
import tn.esprit.forumms.Repository.PostRepository;
import tn.esprit.forumms.Repository.ProductRepository;
import tn.esprit.forumms.Repository.UserRepository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ICommentPostServiceImp implements ICommentPostService{
    public final CommentPostRepository commentPostRepository;
    public final UserRepository userRepository;
    public final PostRepository postRepository;
    public final ProductRepository productRepository;




    @Override
    public List<CommentPost> getAllComments() {
        return commentPostRepository.findAll();
    }

    private List<String> fetchBadWords() {
        List<String> badWords = new ArrayList<>();
        try {
            URL url = new URL("https://docs.google.com/spreadsheets/d/1hIEi2YG3ydav1E06Bzf2mQbGZ12kh2fe4ISgLg_UBuM/export?format=csv");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0) {
                    badWords.add(values[0]);
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return badWords;
    }

    public String convertEmoticonsToEmoji(String text) {
        Map<String, String> emoticonMap = new HashMap<>();
        emoticonMap.put(":)", "\uD83D\uDE42");
        emoticonMap.put(":(", "\uD83D\uDE41");
        emoticonMap.put(":D", "\uD83D\uDE00");
        emoticonMap.put(":P", "\uD83D\uDE1B");
        emoticonMap.put("<3", "\uD83D\uDC97");

        for (Map.Entry<String, String> entry : emoticonMap.entrySet()) {
            String pattern = Pattern.quote(entry.getKey()); // escape any special characters
            text = text.replaceAll(pattern, entry.getValue());
        }

        return text;
    }

    @Override
    public CommentPost addComment(CommentPost commentPost,String idUser,Long idPost) {
        User user=userRepository.findById(idUser).orElse(null);
        Post post=postRepository.findById(idPost).orElse(null);
        if(user!=null&&post!=null&&commentPost.getDescriptionComment()!=null){
            String commentTextWithEmoji = convertEmoticonsToEmoji(commentPost.getDescriptionComment());
            commentPost.setDescriptionComment(commentTextWithEmoji);
            List<String> badWords = fetchBadWords();

            boolean containsBadWord = false;
            for (String badWord : badWords) {
                if (commentPost.getDescriptionComment().toLowerCase().contains(badWord.toLowerCase())) {
                    containsBadWord = true;
                    break;
                }
            }

            if (containsBadWord) {
                return null;
            }
            else {
                commentPost.setUserComment(user);
                commentPost.setPost(post);
                commentPost.setDateCreationComment(new Date());
                return commentPostRepository.save(commentPost);
            }
        }
        else return null;
    }

    @Override
    public CommentPost editComment(CommentPost commentPost, String idUser, Long idComment) {
        User user =userRepository.findById(idUser).orElse(null);
        CommentPost comment =commentPostRepository.findById(idComment).orElse(null);
        if (comment!=null && comment.getUserComment().equals(user)){
            List<String> badWords = fetchBadWords();
            String text = commentPost.getDescriptionComment();
            for (String word : badWords) {
                if (text.toLowerCase().contains(word.toLowerCase())) {
                    return null;
                }
            }
            text = convertEmoticonsToEmoji(text);
            commentPost.setDescriptionComment(text);
            commentPost.setDateCreationComment(comment.getDateCreationComment());
            commentPost.setNbLiked(comment.getNbLiked());
            commentPost.setNbDisliked(comment.getNbDisliked());
            commentPost.setPost(comment.getPost());
            commentPost.setUserComment(comment.getUserComment());
            return commentPostRepository.save(commentPost);
        }
        else return null;
    }

    @Override
    public void deleteComment(Long commentId,String idUser) {
        User user=userRepository.findById(idUser).orElse(null);
        CommentPost comment=commentPostRepository.findById(commentId).orElse(null);
        if(user!=null&&comment!=null){
            if (comment.getUserComment().equals(user))
                commentPostRepository.deleteById(commentId);
        }
    }

    @Override
    public CommentPost getById(Long commentId) {
        return commentPostRepository.findById(commentId).orElse(null);
    }

    @Override
    public CommentPost setProductToComment(Long commentId, Long idProduct) {
        Product product=productRepository.findById(idProduct).orElse(null);
        CommentPost comment=commentPostRepository.findById(commentId).orElse(null);
        if(product!=null&&comment!=null){
            comment.setProductForum(product);
            return commentPostRepository.save(comment);
        }
        return null;
    }

    public int getnbrproduitVendus(String idUser){
        User user=userRepository.findById(idUser).orElse(null);
        List<Product>products=user.getProductListUser();
        List<CartLine>cartLines = new ArrayList<>();
        int productsSold=0;
        for (Product product:products) {
            for (CartLine cartLine:product.getCartLines()) {
                Objects.requireNonNull(cartLines).add(cartLine);
            }
        }

        for (CartLine cartLine: Objects.requireNonNull(cartLines)) {
            if (cartLine.getCart().getCartStatus().equals(CartStatus.CONFIRMED)){
                productsSold=productsSold+cartLine.getQuantity();
            }
        }
        return productsSold;
    }
    @Override
    public List<CommentPost> getCommentsSortedByAverage(Long idPost) {
        Post post=postRepository.findById(idPost).orElse(null);
        if (post!=null){
            List<CommentPost> comments = post.getCommentList();
            Comparator<CommentPost> score = Comparator.comparingDouble(c -> {
                double likes = Optional.ofNullable(c.getNbLiked()).orElse(0L);
                double dislikes = Optional.ofNullable(c.getNbDisliked()).orElse(0L);
                double produitVendus = Optional.of((long) getnbrproduitVendus(c.getUserComment().getIdUser())).orElse(0L);
                double claimsnbr = Optional.of((long) c.getUserComment().getClaimList().size()).orElse(0L);
                return (likes - dislikes)*20+produitVendus*30-claimsnbr*50 / 100.0;
            });

            return comments.stream()
                    .sorted(score.reversed())
                    .collect(Collectors.toList());
        }
        else return null;
    }



}
