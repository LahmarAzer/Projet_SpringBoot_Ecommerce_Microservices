package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.msstore.Entity.DeliveryCompany;

public interface DeliveryCompanyRepository extends JpaRepository<DeliveryCompany, Long> {
}