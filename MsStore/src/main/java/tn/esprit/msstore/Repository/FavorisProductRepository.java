package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.msstore.Entity.FavoriProduct;
import tn.esprit.msstore.Entity.Product;
import tn.esprit.msstore.Entity.User;

import java.util.List;

public interface FavorisProductRepository extends JpaRepository<FavoriProduct,Long> {
    List<FavoriProduct> findByUser_IdUser(String idClient);
    FavoriProduct findByUserAndProduct(User user , Product product);
}
