package com.datmt.keycloak.springbootauth.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Facture implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idFacture;
    private String topicFacture;
    private boolean archive;
    private String reference;
    private float priceTotal;
    private Date datefacture ;
    private String devise;
    @JsonIgnore
    @OneToOne
    private FactureAvoir factureAvoir;

    @JsonIgnore
    @OneToMany (mappedBy = "facture")
    private List<Claim> claims ;

    @JsonIgnore
    @ManyToOne
    private  User user;

    @JsonIgnore
    @OneToOne(mappedBy = "facture")
    private Delivery delivery;
}