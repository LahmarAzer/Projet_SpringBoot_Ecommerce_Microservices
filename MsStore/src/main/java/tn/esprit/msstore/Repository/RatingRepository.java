package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.msstore.Entity.RatingProduct;

public interface RatingRepository extends JpaRepository<RatingProduct,Long> {
    @Query(value = "SELECT AVG(nbr_etoiles_product) FROM rating_product WHERE product_id_product = :productId", nativeQuery = true)
    double findAverageRating(@Param("productId") Long productId);
}
