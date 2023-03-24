package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.msstore.Entity.Sous_CategoryProduct;

public interface SousCategorieRepository extends JpaRepository<Sous_CategoryProduct,Long> {
}
