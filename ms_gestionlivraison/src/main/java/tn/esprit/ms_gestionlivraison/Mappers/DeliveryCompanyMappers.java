package tn.esprit.ms_gestionlivraison.Mappers;

import tn.esprit.ms_gestionlivraison.DTOEntities.DeliveryCompanyDTO;
import tn.esprit.ms_gestionlivraison.Entities.DeliveryCompany;

public class DeliveryCompanyMappers {
    public static DeliveryCompanyDTO mapToDto(DeliveryCompany deliveryCompany) {

        DeliveryCompanyDTO deliveryCompanyDTO = DeliveryCompanyDTO.builder()
                .id_company(deliveryCompany.getId_company())
                .nameCompany(deliveryCompany.getNameCompany())
                .emailCompany(deliveryCompany.getEmailCompany())
                .adress(deliveryCompany.getAdress())
                .representativeName(deliveryCompany.getRepresentativeName())
                .Code_country(deliveryCompany.getCode_country())
                .build();
        return deliveryCompanyDTO;
    }

    public static DeliveryCompany mapToEntity(DeliveryCompanyDTO deliveryCompanyDTO) {
        DeliveryCompany deliveryCompany = DeliveryCompany.builder()
                .id_company(deliveryCompanyDTO.getId_company())
                .nameCompany(deliveryCompanyDTO.getNameCompany())
                .representativeName(deliveryCompanyDTO.getRepresentativeName())
                .build();
        return deliveryCompany;
    }
}


