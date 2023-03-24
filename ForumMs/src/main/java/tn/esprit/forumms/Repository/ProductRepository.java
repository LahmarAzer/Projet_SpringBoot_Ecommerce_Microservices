package tn.esprit.forumms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.forumms.Entity.Product;
public interface ProductRepository extends JpaRepository<Product,Long> {
}
