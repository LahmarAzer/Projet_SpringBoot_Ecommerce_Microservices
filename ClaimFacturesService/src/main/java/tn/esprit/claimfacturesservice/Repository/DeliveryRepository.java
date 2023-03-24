package tn.esprit.claimfacturesservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.claimfacturesservice.Entities.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
}
