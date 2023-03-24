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
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryMen implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_DeliverMen ;
    private String Name ;
    private int PhoneNumber ;
    private Boolean availability ;
    private int score ;
    @Enumerated(EnumType.STRING)
    private Level level ;
    @JsonIgnore
    @ManyToOne
    public  DeliveryCompany deliveryCompany;

    @JsonIgnore
    @OneToMany(mappedBy = "deliveryMen")
    public List<Delivery>deliveries ;
    @JsonIgnore
    @ManyToOne
    public Adress adresslivreur ;

}