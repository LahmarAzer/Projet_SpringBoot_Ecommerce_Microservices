package tn.esprit.ms_gestionlivraison.Mappers;

import tn.esprit.ms_gestionlivraison.DTOEntities.DeliveryDTO;
import tn.esprit.ms_gestionlivraison.Entities.Delivery;

public class DeliveryMappers {
    public static DeliveryDTO mapToDto(Delivery delivery){

        DeliveryDTO deliveryDTO = DeliveryDTO.builder()
                .id_Delivery(delivery.getId_Delivery())
                .deliverytime(delivery.getDeliverytime())
                .deliveryPrice(delivery.getDeliveryPrice())
                .statusDelivery(delivery.getStatusDelivery())
                .build();
        return deliveryDTO;
    }
    public static Delivery mapToEntity(DeliveryDTO deliveryDTO){
        Delivery delivery = Delivery.builder()
                .id_Delivery(deliveryDTO.getId_Delivery())
                .deliverytime(deliveryDTO.getDeliverytime())
                .deliveryPrice(deliveryDTO.getDeliveryPrice())
                .build();
        return delivery;
    }
}
