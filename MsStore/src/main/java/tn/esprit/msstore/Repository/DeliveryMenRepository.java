package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.msstore.Entity.DeliveryMen;

public interface DeliveryMenRepository extends JpaRepository<DeliveryMen, Long> {
}