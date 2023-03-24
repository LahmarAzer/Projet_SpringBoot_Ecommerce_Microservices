package tn.esprit.claimfacturesservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.claimfacturesservice.Entities.Claim;

public interface ClaimRepo extends JpaRepository<Claim, Long> {
  //List<Claim> findByUser(User user);
  // List<Claim>getClaimById();

//@Query("SELECT  c FROM  Claim  c  WHERE c.userclaim.idUser= :userId")
       // @Query("SELECT c FROM Claim c WHERE c.userclaim.idUser = :userId AND c.facture.productList = :productId AND c.type = :type")
        //List<Claim> findDuplicateComplaints(@Param("userId") Long userId, @Param("productId") Long productId, @Param("type") ComplaintType type);


}
