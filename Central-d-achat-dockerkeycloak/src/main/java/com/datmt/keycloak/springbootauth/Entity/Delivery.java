package com.datmt.keycloak.springbootauth.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Delivery implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Delivery;
    private float deliveryPrice;
    @Temporal(TemporalType.DATE)
    private Date deliverytime;
    @Temporal(TemporalType.DATE)
    private Date expectedDeliveryDate;
    @Enumerated(EnumType.STRING)
    private StatusDelivery statusDelivery;
    @Enumerated(EnumType.STRING)
    private  MeanOfTransport meanOfTransport ;
    private double weight;
    private int customerSatisfaction;
    @JsonIgnore
    @ManyToOne
    private DeliveryCompany deliveryCompany;
    @JsonIgnore
    @ManyToOne
    private DeliveryMen deliveryMen ;
    @JsonIgnore
    @ManyToOne
    private Adress adress;
    @JsonIgnore
    @OneToOne()
    private Cart cart ;

    @JsonIgnore
    @OneToOne
    private Facture facture;

}
