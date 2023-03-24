package tn.esprit.claimfacturesservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.claimfacturesservice.Entities.FactureAvoir;

public interface FactureAvoirRepository extends JpaRepository<FactureAvoir,Long> {
}
