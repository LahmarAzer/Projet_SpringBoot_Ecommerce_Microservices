package tn.esprit.ms_gestionlivraison.DTOEntities;

import lombok.*;
import tn.esprit.ms_gestionlivraison.Entities.Delivery;
import tn.esprit.ms_gestionlivraison.Entities.DeliveryMen;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class DeliveryCompanyDTO implements Serializable {
    private Long id_company ;
    private String nameCompany ;
    private String emailCompany ;
    private String representativeName ;
    private  float Code_country ;
    private String adress ;
    private List<Delivery> deliveryList;
    public List<DeliveryMen>deliveryMen;
}
