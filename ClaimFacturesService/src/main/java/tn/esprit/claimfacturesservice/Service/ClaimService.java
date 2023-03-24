package tn.esprit.claimfacturesservice.Service;

import tn.esprit.claimfacturesservice.Entities.Claim;
import tn.esprit.claimfacturesservice.Entities.Delivery;
import tn.esprit.claimfacturesservice.Entities.StatusClaim;

import java.text.ParseException;
import java.util.List;

public interface ClaimService {
    public Claim addClaim(Claim claim);
    public Claim UpdateClaim(Claim claim);
    public Boolean DeleteClaim(Long id);
    public Claim retrieveclaimById(Long id);
    public List<Claim> retrieveAllclaim();
    public Claim UpdateClaimStatut(Long idClaim , StatusClaim newStatut);
    public boolean isOwner(long idclaim);
    public Boolean DateValideClaim(Long Idclaim, Long Iddelivery);

    public String isClaimValid(Claim claim, String invoiceNumber) ;
    public String banUser(String supplierId);


   public String isPurchase(String refProduct , String idUser);
//   public boolean isFraudulentComplaint(Claim claim);

   // public void createClaim(Long supplierId);
   // public void sendSimpleMessage(String from,String to, String subject, String text);
   // public Claim validerClaimProduit(Claim claim) ;
}


