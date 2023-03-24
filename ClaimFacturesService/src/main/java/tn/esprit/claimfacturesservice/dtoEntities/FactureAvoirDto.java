package tn.esprit.claimfacturesservice.dtoEntities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tn.esprit.claimfacturesservice.Entities.Facture;


import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class FactureAvoirDto implements Serializable {
    private final Long idFactureAvoir;
    private final String description;
    private final Facture facture;



}
