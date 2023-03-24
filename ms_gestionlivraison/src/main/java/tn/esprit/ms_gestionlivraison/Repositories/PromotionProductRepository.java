package tn.esprit.ms_gestionlivraison.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ms_gestionlivraison.Entities.PromotionProduct;

public interface PromotionProductRepository extends JpaRepository<PromotionProduct, Long> {
}