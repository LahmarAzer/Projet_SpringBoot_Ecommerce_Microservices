package tn.esprit.claimfacturesservice.Mapper;

import tn.esprit.claimfacturesservice.Entities.FactureAvoir;
import tn.esprit.claimfacturesservice.dtoEntities.FactureAvoirDto;

public class FactureAvoirMapper {

    public static FactureAvoirDto mapFactureAvoirToDto(FactureAvoir factureAvoir) {

        FactureAvoirDto factureAvoirDto = FactureAvoirDto.builder()
                .idFactureAvoir(factureAvoir.getIdFactureAvoir())
                .description(factureAvoir.getDescription())
                .build();
        return factureAvoirDto;
    }
    public static FactureAvoir mapFactureAvoirDtoToEntity(FactureAvoirDto factureAvoirDto){

        FactureAvoir factureAvoir = FactureAvoir.builder()
                .idFactureAvoir(factureAvoirDto.getIdFactureAvoir())
                .description(factureAvoirDto.getDescription())
                .build();
        return factureAvoir;
    }
}