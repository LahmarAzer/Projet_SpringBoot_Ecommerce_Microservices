package tn.esprit.ms_gestionlivraison.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriProduct implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idFavori ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idProduct")
    private Product product;

    private Date dateAjout;

//    public FavoriProduct(User user, Product product, Date dateAjout) {
//        this.user = user;
//        this.product = product;
//        this.dateAjout = dateAjout;
//    }
}
