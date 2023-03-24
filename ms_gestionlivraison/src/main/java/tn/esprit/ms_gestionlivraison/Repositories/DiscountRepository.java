package tn.esprit.ms_gestionlivraison.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ms_gestionlivraison.Entities.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}