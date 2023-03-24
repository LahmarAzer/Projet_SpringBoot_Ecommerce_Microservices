package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.msstore.Entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
