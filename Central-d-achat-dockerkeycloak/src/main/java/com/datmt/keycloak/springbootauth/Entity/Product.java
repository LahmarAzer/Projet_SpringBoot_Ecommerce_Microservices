package com.datmt.keycloak.springbootauth.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
@ToString
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idProduct;
    private String descriptionProduct;
    private float priceProduct;
    private Long quantityProduct;
    private String nameProduct;
    private String referenceProduct;
    //private String imageProduct;
    @Lob
    private byte[] imageProduct;
    private float discountProduct;
    private String marqueProduct;
    @Temporal(TemporalType.DATE)
    private Date dateCreationProduct;
    @JsonIgnore
    @ManyToOne
    private CategoryProduct categoryProduct;

    @JsonIgnore
    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
    private List<RatingProduct> ratingProductList;

    @JsonIgnore
    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
    private List<CartLine> cartLines;

    @JsonIgnore
    @ManyToOne
    private User userProduct;

    @JsonIgnore
    @OneToOne
    private CommentPost comment;

    @JsonIgnore
    @ManyToOne
    private Sous_CategoryProduct sousCategorie;

    @JsonIgnore
    @OneToMany(mappedBy = "product" ,cascade = CascadeType.ALL)
    private List<FavoriProduct> favoriProducts;

    @JsonIgnore
    @OneToOne
    private Discount discount;

}
