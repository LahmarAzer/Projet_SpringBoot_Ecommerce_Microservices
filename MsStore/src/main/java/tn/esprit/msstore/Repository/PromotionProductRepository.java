package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.msstore.Entity.PromotionProduct;
import tn.esprit.msstore.Entity.Sous_CategoryProduct;

import java.util.Date;

public interface PromotionProductRepository extends JpaRepository<PromotionProduct,Long> {
    PromotionProduct findByIdAndSousCategoriePromotionAndEndDateAfter(Long idPromotion ,Sous_CategoryProduct sousCategorie, Date date);
}
