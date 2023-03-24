package tn.esprit.ms_gestionlivraison.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ms_gestionlivraison.Entities.FavoriProduct;

public interface FavoriProductRepository extends JpaRepository<FavoriProduct, Long> {
}