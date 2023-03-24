package tn.esprit.claimfacturesservice.Mapper;

import tn.esprit.claimfacturesservice.Entities.Claim;
import tn.esprit.claimfacturesservice.Entities.Facture;
import tn.esprit.claimfacturesservice.dtoEntities.ClaimDto;
import tn.esprit.claimfacturesservice.dtoEntities.FactureDto;

public class ClaimMapper {


    public static ClaimDto mapclaimToDto(Claim claim) {

        ClaimDto claimdto = ClaimDto.builder()
                .idClaim(claim.getIdClaim())
                .dateCreationClaim(claim.getDateCreationClaim())
                .descriptionClaim(claim.getDescriptionClaim())
                .categoryClaim(claim.getCategoryClaim())
                .statusClaim(claim.getStatusClaim())
                .userclaim(claim.getUserclaim())
                .facture(claim.getFacture())
                .deliveryMen(claim.getDeliveryMen())
                .build();
        return claimdto;
    }
    public static Claim mapclaimDtoToEntity(ClaimDto claimDto){

        Claim claim = Claim.builder()
                .idClaim(claimDto.getIdClaim())
                .dateCreationClaim(claimDto.getDateCreationClaim())
                .descriptionClaim(claimDto.getDescriptionClaim())
                .categoryClaim(claimDto.getCategoryClaim())
                .statusClaim(claimDto.getStatusClaim())
                .userclaim(claimDto.getUserclaim())
                .facture(claimDto.getFacture())
                .deliveryMen(claimDto.getDeliveryMen())
                .build();
        return claim;
    }
}

