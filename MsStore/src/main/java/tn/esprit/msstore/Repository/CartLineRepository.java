package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.msstore.Entity.CartLine;

import java.util.List;

public interface CartLineRepository extends JpaRepository<CartLine,Long> {
    @Query("SELECT DISTINCT cl.product.idProduct FROM CartLine cl WHERE cl.cart.cartStatus = 'CONFIRMED'")
    List<Long> findOrderedProductIds();
}
