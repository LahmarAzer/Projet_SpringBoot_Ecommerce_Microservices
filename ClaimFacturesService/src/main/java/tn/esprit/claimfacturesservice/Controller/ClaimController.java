package tn.esprit.claimfacturesservice.Controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.claimfacturesservice.Entities.Claim;
import tn.esprit.claimfacturesservice.Entities.Delivery;
import tn.esprit.claimfacturesservice.Entities.StatusClaim;
import tn.esprit.claimfacturesservice.Service.ClaimService;
import tn.esprit.claimfacturesservice.Service.EmailService;
import tn.esprit.claimfacturesservice.dtoEntities.MailRequest;
import tn.esprit.claimfacturesservice.dtoEntities.MailResponse;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor

@RequestMapping("/claimFacture/claim")
public class ClaimController {
    @Autowired
    EmailService emailService;
    @Autowired
    ClaimService claimService;
    // "dateCreationClaim":"28/06/2023";

    @GetMapping("/hello")
    public String hello() {
        return ("hello claimFacture");
    }

    @PostMapping()
    public Claim addClaim(@RequestBody Claim claim) {
        return claimService.addClaim(claim);
    }


    @PutMapping()
    public Claim UpdateClaim(@RequestBody Claim claim) {
        return claimService.UpdateClaim(claim);
    }


    @PutMapping("/{id}/{newStatut}")
    public Claim UpdateClaimStatut(@PathVariable Long id, @PathVariable StatusClaim newStatut) {
        return claimService.UpdateClaimStatut(id, newStatut);
    }


    @GetMapping("/{id}")
    public Claim retrieveclaimById(@PathVariable Long id) {
        return claimService.retrieveclaimById(id);
    }
@RolesAllowed("admin")
    @GetMapping()
    public List<Claim> retrieveAllclaim() {
        return claimService.retrieveAllclaim();
    }

    @DeleteMapping("/{id}")
    public Boolean DeleteClaim(@PathVariable Long id) {
        return claimService.DeleteClaim(id);
    }


    @GetMapping("/date/{Idclaim}/{Iddelivery}")
    public boolean DateValideClaim(@PathVariable Long Idclaim, @PathVariable Long Iddelivery) {

        return claimService.DateValideClaim(Idclaim, Iddelivery);
    }

    @GetMapping("/isowner/{Idclaim}")
    public boolean Isowner(@PathVariable Long Idclaim) {
        return claimService.isOwner(Idclaim);
    }

    @GetMapping("/IsPurchase/{refProduct}/{idUser}")
    public ResponseEntity<String> IsPurchase(@PathVariable String refProduct,
                                             @PathVariable String idUser) {
        return ResponseEntity.ok(claimService.isPurchase(refProduct, idUser));

    }

    @PutMapping("/bann/{id}")
    public String bann(@PathVariable String id) {
        return claimService.banUser(id);
    }

    @PostMapping("/claimvalid/{invoiceNumber}")
    public ResponseEntity<String> claimvalid(@RequestBody Claim claim, @PathVariable String invoiceNumber) {
       return ResponseEntity.ok(claimService.isClaimValid(claim, invoiceNumber));
    }
























    // @PostMapping("/{claimId}/{invoiceNumber}")
//  public void isClaimValid (@PathVariable Long claimId ,@PathVariable String invoiceNumber)  {
//      claimService.isClaimValid(claimId,invoiceNumber);
//   }

//    @PostMapping("/sendemail")
//    public void sendEmail(@RequestBody String from ,@RequestBody String to, @RequestBody String subject, @RequestBody String text) {
//
//        claimService.sendSimpleMessage(from,to, subject, text);
//        ResponseEntity.ok("Request processed successfully");
//
//    }



//    @PostMapping("/create")
//    public void createClaim(@RequestBody Long id) {
//        claimService.createClaim(id);
//    }



}
