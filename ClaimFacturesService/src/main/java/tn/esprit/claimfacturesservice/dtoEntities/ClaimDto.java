package tn.esprit.claimfacturesservice.dtoEntities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tn.esprit.claimfacturesservice.Entities.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Builder
@Getter
@Setter
@AllArgsConstructor
public class ClaimDto implements Serializable {


    private final Long idClaim;
    private final String descriptionClaim;
    private  final Date dateCreationClaim;
    private final CategoryClaim categoryClaim;
    private final StatusClaim statusClaim;

    private final User userclaim;
    private  final Facture facture;
    private final   DeliveryMen deliveryMen;
}
