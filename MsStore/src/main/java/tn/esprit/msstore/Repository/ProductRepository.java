package tn.esprit.msstore.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.msstore.Entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Override
    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p, AVG(rp.nbrEtoilesProduct) AS avgRating, SUM(cl.quantity) AS totalQuantity " +
            "FROM Product p " +
            "INNER JOIN CartLine cl ON p.idProduct = cl.product.idProduct " +
            "INNER JOIN RatingProduct rp ON p.idProduct = rp.product.idProduct " +
            "WHERE p.idProduct IN :orderedProductIds " +
            "GROUP BY p.idProduct " +
            "ORDER BY avgRating DESC, totalQuantity DESC")
    List<Product> findProductAvgRatingAndTotalQuantity(@Param("orderedProductIds") List<Long> orderedProductIds);

    List<Product> findByNameProductContainingIgnoreCaseAndMarqueProductContainingIgnoreCase(String nameProduct , String marqueProduct);

    @Query("SELECT p FROM Product p JOIN p.categoryProduct c WHERE c.nameCategoryProduct = :categorieNom")
    List<Product> findByCategorie(@Param("categorieNom") String categorieNom);


}
