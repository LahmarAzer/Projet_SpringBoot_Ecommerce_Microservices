package tn.esprit.ms_gestionlivraison.Service;

import tn.esprit.ms_gestionlivraison.Entities.DeliveryMen;

import java.util.List;

public interface DeliveryMenIServiceIMP {
    DeliveryMen AddDeliveryMen (DeliveryMen deliveryMen);
    DeliveryMen GetdeliveryMenId(Long id_DeliverMen);
    List<DeliveryMen>GetAllDelivery();
}
