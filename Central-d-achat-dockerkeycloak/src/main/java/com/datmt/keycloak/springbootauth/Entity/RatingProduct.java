package com.datmt.keycloak.springbootauth.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingProduct implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idRaitingProduct;
    private int nbrEtoilesProduct;
    private String reviewProduct;

    @ManyToOne
    @JsonIgnore
    private Product product ;

    @ManyToOne
    private User userRating;
}
