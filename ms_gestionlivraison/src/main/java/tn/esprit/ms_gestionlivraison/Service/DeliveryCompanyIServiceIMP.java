package tn.esprit.ms_gestionlivraison.Service;

import tn.esprit.ms_gestionlivraison.DTOEntities.DeliveryCompanyDTO;
import tn.esprit.ms_gestionlivraison.DTOEntities.DeliveryDTO;

import java.util.List;

public interface DeliveryCompanyIServiceIMP {
    DeliveryCompanyDTO addCompany(DeliveryCompanyDTO deliveryCompanyDTO);
    DeliveryCompanyDTO updateCompany(DeliveryCompanyDTO deliveryCompanyDTO);
    DeliveryCompanyDTO getCompanyId(Long id_company);
    List<DeliveryCompanyDTO> getAllCompany();
    void removeCompany(Long  id_company);
}
