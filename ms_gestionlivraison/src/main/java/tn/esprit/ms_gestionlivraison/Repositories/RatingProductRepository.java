package tn.esprit.ms_gestionlivraison.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ms_gestionlivraison.Entities.RatingProduct;

public interface RatingProductRepository extends JpaRepository<RatingProduct, Long> {
}