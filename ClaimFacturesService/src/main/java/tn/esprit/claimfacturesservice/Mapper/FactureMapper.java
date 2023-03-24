package tn.esprit.claimfacturesservice.Mapper;

import tn.esprit.claimfacturesservice.Entities.Facture;
import tn.esprit.claimfacturesservice.Entities.FactureAvoir;
import tn.esprit.claimfacturesservice.dtoEntities.FactureAvoirDto;
import tn.esprit.claimfacturesservice.dtoEntities.FactureDto;

public class FactureMapper {
    public static FactureDto mapfactureToDto(Facture facture) {

        FactureDto factureDto = FactureDto.builder()
                .idFacture(facture.getIdFacture())
                .datefacture(facture.getDatefacture())
                .topicFacture(facture.getTopicFacture())
                .priceTotal(facture.getPriceTotal())
                .reference(facture.getReference())
                .factureAvoir(facture.getFactureAvoir())
                .claims(facture.getClaims())
                .user(facture.getUser())
                .archive(facture.isArchive())
                .delivery(facture.getDelivery())
                .build();
        return factureDto;
    }
    public static Facture mapRatingDtoToEntity(FactureDto factureDto){

        Facture facture = Facture.builder()
                .idFacture(factureDto.getIdFacture())
                .datefacture(factureDto.getDatefacture())
                .topicFacture(factureDto.getTopicFacture())
                .priceTotal(factureDto.getPriceTotal())
                .reference(factureDto.getReference())
                .factureAvoir(factureDto.getFactureAvoir())
                .claims(factureDto.getClaims())
                .user(factureDto.getUser())
                .archive(factureDto.isArchive())
                .delivery(factureDto.getDelivery())
                .build();
        return facture;
    }
}
