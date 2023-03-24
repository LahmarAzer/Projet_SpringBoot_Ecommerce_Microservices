package tn.esprit.msstore.dtoEntities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tn.esprit.msstore.Entity.CategoryProduct;
import tn.esprit.msstore.Entity.CommentPost;
import tn.esprit.msstore.Entity.RatingProduct;
import tn.esprit.msstore.Entity.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * A DTO for the {@link tn.esprit.msstore.Entity.Product} entity
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
public class ProductDto implements Serializable {
    private final Long idProduct;
    private final String descriptionProduct;
    private final float priceProduct;
    private final Long quantityProduct;
    private final String nameProduct;
    private final String referenceProduct;
    private final String imageProduct;
    private final float discountProduct;
    private final String marqueProduct;
    private final Date dateCreationProduct;
    private final CategoryProduct categoryProduct;
    private final List<RatingProduct> ratingProductList;
    private final List<User> usersOrder;
    private final List<User> usersCart;
    private final User userProduct;
    private final CommentPost comment;
}