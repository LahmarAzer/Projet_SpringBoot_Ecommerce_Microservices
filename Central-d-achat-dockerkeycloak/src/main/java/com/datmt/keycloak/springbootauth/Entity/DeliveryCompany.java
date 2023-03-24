package com.datmt.keycloak.springbootauth.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryCompany implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_company ;
    private String nameCompany ;
    private String emailCompany ;
    private String representativeName ;
    private  float Code_country ;
    private String Adress ;
    @JsonIgnore
    @OneToMany(mappedBy = "deliveryCompany")
    private List<Delivery> deliveryList;
    @JsonIgnore
    @OneToMany(mappedBy = "deliveryCompany")
    public List<DeliveryMen>deliveryMen;

    @OneToMany(mappedBy = "deliveryCompany")
    private List<User>userrepresentative;


}
