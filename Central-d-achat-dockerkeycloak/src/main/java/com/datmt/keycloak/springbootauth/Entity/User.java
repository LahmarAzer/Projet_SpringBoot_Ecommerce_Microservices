package com.datmt.keycloak.springbootauth.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    // @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String idUser;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    private String phonenumber;

    private String country;

    private String gouvernment;

    private boolean etat;

    private Long phone;

    private boolean disponibilite;

    private int age;

    private  int nbrAvertissment ;

    private  boolean banned;




    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<FavoriProduct> favoriProductList;

    @JsonIgnore
    @OneToMany(mappedBy = "userclaim")
    private List<Claim> claimList;
    @JsonIgnore
    @OneToMany(mappedBy = "userRating")
    private List<RatingProduct> ratingProductList;

    @JsonIgnore
    @OneToMany(mappedBy = "userProduct")
    private List<Product> productListUser;
    @JsonIgnore
    @OneToMany(mappedBy = "usedBy")
    private List<Discount> discounts ;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Cart> carts ;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<LikeComment> likeComments;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<DislikeComment> dislikeComments;
    @JsonIgnore
    @OneToMany(mappedBy = "userPost")
    private List<Post>posts;
    @JsonIgnore
    @OneToMany(mappedBy = "userComment")
    List<CommentPost>commentList;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Facture> factureList;


    @ManyToOne
    private DeliveryCompany deliveryCompany;

}