package tn.esprit.forumms.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.forumms.Entity.CommentPost;
import tn.esprit.forumms.Entity.Product;
import tn.esprit.forumms.Entity.User;
import tn.esprit.forumms.Repository.CommentPostRepository;
import tn.esprit.forumms.Repository.ProductRepository;
import tn.esprit.forumms.Repository.UserRepository;

@Service
@AllArgsConstructor
public class IProductServiceImp implements IProductService {

    public final ProductRepository productRepository;
    public final CommentPostRepository commentPostRepository;
    public final UserRepository userRepository;

    @Override
    public Product addProduct(Product product, String idUser, long idcomment) {
        CommentPost c =commentPostRepository.findById(idcomment).orElse(null);
        User u =userRepository.findById(idUser).orElse(null);
        product.setComment(c);
        product.setUserProduct(u);
        return productRepository.save(product);
    }
}
