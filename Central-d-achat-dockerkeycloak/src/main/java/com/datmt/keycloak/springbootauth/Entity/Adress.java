package com.datmt.keycloak.springbootauth.Entity;

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
public class Adress implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdress ;
    private String street ;
    private String city ;
    private String state ;
    private  double Latitude ;
    private double Longitude ;
    @JsonIgnore
    @OneToMany(mappedBy = "adress")
    List<Delivery>deliveries;
    @JsonIgnore
    @OneToMany(mappedBy = "adresslivreur")
    List<DeliveryMen>deliveryMen ;

}
