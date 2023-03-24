package tn.esprit.ms_gestionlivraison.Service;

import tn.esprit.ms_gestionlivraison.DTOEntities.DeliveryDTO;
import tn.esprit.ms_gestionlivraison.Entities.Delivery;
import tn.esprit.ms_gestionlivraison.Entities.MeanOfTransport;
import tn.esprit.ms_gestionlivraison.Entities.StatusDelivery;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DeliveryIServiceIMP {
    DeliveryDTO addDelivery(DeliveryDTO deliveryDTO);

    DeliveryDTO updateDelivery(DeliveryDTO deliveryDTO);

    DeliveryDTO getDeliveryId(Long idLivraison);

    List<DeliveryDTO> getAllDelivery();

    void removeDelivery(Long idLivraison);


    @Transactional
    void assignDeliveryToDeliveryMan(Long idDelivery);
      @Transactional
      MeanOfTransport assignMeansOfTransport(Long idDelivery);

    @Transactional
    Delivery assignDeliveryToDriverByAvailabilty(Long id_Delivery);

    @Transactional
    Delivery AssignCartToDelivery(Long id_Delivery, Long idCart);

    @Transactional
    void assignDeliveryToBestShippingCompany(Delivery delivery);

    @Transactional
    void updateDeliveryStatus(long id_Delivery, StatusDelivery newStatus);
    @Transactional
    void unassignDeliveryManOnCancelled(Long id_Delivery);
    @Transactional
    void updateDeliveryManScore(Long id_DeliverMen, int score);
    @Transactional
    List<Map<String, Object>> getDeliveriesPerDay(Date startDate, java.util.Date endDate);
}
