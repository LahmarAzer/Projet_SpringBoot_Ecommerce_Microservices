package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.msstore.Entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}