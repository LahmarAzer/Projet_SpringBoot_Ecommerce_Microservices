package tn.esprit.claimfacturesservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.claimfacturesservice.Entities.Product;
import tn.esprit.claimfacturesservice.Entities.User;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Product findByUserProductIdUser(Long iduser);
    Product findByUserProduct(User user);
    Product findByReferenceProduct(String refProduct);

}
