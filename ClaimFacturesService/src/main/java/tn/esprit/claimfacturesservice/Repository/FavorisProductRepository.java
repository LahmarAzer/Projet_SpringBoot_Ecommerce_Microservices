package tn.esprit.claimfacturesservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.claimfacturesservice.Entities.FavoriProduct;

import java.util.List;

public interface FavorisProductRepository extends JpaRepository<FavoriProduct,Long> {
    List<FavoriProduct> findByUser_IdUser(Long idClient);
}
