package com.datmt.keycloak.springbootauth.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Claim implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idClaim;
    private String descriptionClaim;
    @Temporal(TemporalType.DATE)
    private Date dateCreationClaim;
    @Enumerated(EnumType.STRING)
    private CategoryClaim categoryClaim;
    @Enumerated(EnumType.STRING)
    private StatusClaim statusClaim;
    private String referencefacture;




    @JsonIgnore
    @ManyToOne
    private User userclaim;

    @JsonIgnore
    @ManyToOne
    private Facture facture;

    @ManyToOne()
    private  DeliveryMen deliveryMen;
}