package tn.esprit.claimfacturesservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.claimfacturesservice.Entities.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
