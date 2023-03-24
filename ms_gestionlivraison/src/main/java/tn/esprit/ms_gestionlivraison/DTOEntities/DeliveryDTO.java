package tn.esprit.ms_gestionlivraison.DTOEntities;

import lombok.*;
import tn.esprit.ms_gestionlivraison.Entities.DeliveryCompany;
import tn.esprit.ms_gestionlivraison.Entities.StatusDelivery;

import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@Builder
public class DeliveryDTO implements Serializable {

    private Long id_Delivery;
    private float deliveryPrice;
    private Date deliverytime;
    private Date expectedDeliveryDate;
    private StatusDelivery statusDelivery;
    private DeliveryCompany deliveryCompany;
    private double weight;
    private int customerSatisfaction;

}

