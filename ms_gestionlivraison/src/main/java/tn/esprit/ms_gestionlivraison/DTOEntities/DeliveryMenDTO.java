package tn.esprit.ms_gestionlivraison.DTOEntities;

import lombok.*;
import tn.esprit.ms_gestionlivraison.Entities.DeliveryCompany;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class DeliveryMenDTO implements Serializable {
    private Long id_DeliverMen ;
    private String Name ;
    private int PhoneNumber ;
    private String adress ;
    private boolean availability ;
    public DeliveryCompany deliveryCompany;
}
