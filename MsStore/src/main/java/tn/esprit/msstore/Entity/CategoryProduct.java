package tn.esprit.msstore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryProduct implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idCategoryProduct;
    private String nameCategoryProduct;
    @JsonIgnore
    @OneToMany(mappedBy = "categoryProduct",cascade = CascadeType.ALL)
    private List<Product> productListCategory;

    @OneToMany(mappedBy = "categoryPost")
    private List<Post>posts;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Sous_CategoryProduct> sous_categoryProducts ;
}

