package tn.esprit.ms_gestionlivraison.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.ms_gestionlivraison.DTOEntities.DeliveryCompanyDTO;
import tn.esprit.ms_gestionlivraison.Entities.DeliveryCompany;
import tn.esprit.ms_gestionlivraison.Mappers.DeliveryCompanyMappers;
import tn.esprit.ms_gestionlivraison.Repositories.DeliveryCompanyRepository;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@AllArgsConstructor
public class DeliveryCompanyIService implements DeliveryCompanyIServiceIMP{
    DeliveryCompanyRepository deliveryCompanyRepository ;
    @Override
    public DeliveryCompanyDTO addCompany(DeliveryCompanyDTO deliveryCompanyDTO) {
        DeliveryCompany deliveryCompany = deliveryCompanyRepository.save(DeliveryCompanyMappers.mapToEntity(deliveryCompanyDTO));
        return DeliveryCompanyMappers.mapToDto(deliveryCompany);
    }

    @Override
    public DeliveryCompanyDTO updateCompany(DeliveryCompanyDTO deliveryCompanyDTO) {

        DeliveryCompany deliveryCompany = deliveryCompanyRepository.save(DeliveryCompanyMappers.mapToEntity(deliveryCompanyDTO));
            return DeliveryCompanyMappers.mapToDto(deliveryCompany);

    }

    @Override
    public DeliveryCompanyDTO getCompanyId(Long id_company) {

        DeliveryCompany deliveryCompany = deliveryCompanyRepository.findById(id_company).orElse(null);
        if (deliveryCompany != null) {
            return DeliveryCompanyMappers.mapToDto(deliveryCompany);
        }
        return null ;
    }

    @Override
    public List<DeliveryCompanyDTO> getAllCompany() {
        List<DeliveryCompany> deliveryCompanies = deliveryCompanyRepository.findAll();
        if (deliveryCompanies != null) {
            return deliveryCompanies.stream().
                    map(DeliveryCompany -> DeliveryCompanyMappers.mapToDto(tn.esprit.ms_gestionlivraison.Entities.DeliveryCompany.builder().build()))
                    .collect(Collectors.toList());
        }
        return  null ;
    }

    @Override
    public void removeCompany(Long id_company) {
        DeliveryCompany deliveryCompany = deliveryCompanyRepository.findById(id_company).orElse(null);
        if (deliveryCompany != null ) {
            deliveryCompanyRepository.deleteById(id_company);
        }
        log.info("delivery company n'existe pas ");
    }
}
