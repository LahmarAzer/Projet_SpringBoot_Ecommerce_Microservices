package tn.esprit.forumms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.forumms.Entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim,Long> {
}
