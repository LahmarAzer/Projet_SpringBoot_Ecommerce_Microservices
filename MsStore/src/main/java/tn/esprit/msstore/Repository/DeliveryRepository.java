package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.msstore.Entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}