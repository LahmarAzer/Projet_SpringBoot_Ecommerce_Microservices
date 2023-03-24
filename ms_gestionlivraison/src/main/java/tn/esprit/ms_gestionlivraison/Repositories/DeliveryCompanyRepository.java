package tn.esprit.ms_gestionlivraison.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ms_gestionlivraison.Entities.DeliveryCompany;

public interface DeliveryCompanyRepository extends JpaRepository<DeliveryCompany,Long> {
}
