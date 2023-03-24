package tn.esprit.ms_gestionlivraison.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ms_gestionlivraison.Entities.Delivery;
import tn.esprit.ms_gestionlivraison.Entities.DeliveryCompany;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery,Long>  {
    @Transactional
    List<Delivery> findByDeliveryCompany(DeliveryCompany deliveryCompany);
    List<Delivery> findByDeliverytimeBetween(Date startDate, Date endDate);
}
