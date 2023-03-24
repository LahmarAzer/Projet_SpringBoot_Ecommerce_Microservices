package com.datmt.keycloak.springbootauth.Entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sous_CategoryProduct implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String nom;
    @ManyToOne()
    private CategoryProduct category;

    @OneToMany(mappedBy = "sousCategorie" , cascade = CascadeType.ALL)
    private List<Product> productListSousCategories;

    @OneToMany(mappedBy = "sousCategoriePromotion")
    private List<PromotionProduct> promotionProductsSousCategorie;

}
