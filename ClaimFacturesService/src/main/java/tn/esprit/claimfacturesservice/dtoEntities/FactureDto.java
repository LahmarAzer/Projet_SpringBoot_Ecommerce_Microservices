package tn.esprit.claimfacturesservice.dtoEntities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tn.esprit.claimfacturesservice.Entities.Claim;
import tn.esprit.claimfacturesservice.Entities.Delivery;
import tn.esprit.claimfacturesservice.Entities.FactureAvoir;
import tn.esprit.claimfacturesservice.Entities.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Builder
@Getter
@Setter
@AllArgsConstructor
public class FactureDto implements Serializable {




    private final Long idFacture;
    private final  String topicFacture;
    private final  boolean archive;
    private  final String reference;
    private  final float priceTotal;
    private final  Date datefacture ;

    private  final FactureAvoir factureAvoir;
    private final List<Claim> claims ;
    private final User user;
    private final Delivery delivery;
}
