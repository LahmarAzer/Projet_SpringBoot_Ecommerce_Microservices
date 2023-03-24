package tn.esprit.ms_gestionlivraison.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ms_gestionlivraison.Entities.DeliveryMen;
import tn.esprit.ms_gestionlivraison.Repositories.DeliveryMenRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryMenIService implements DeliveryMenIServiceIMP {
    @Autowired
    DeliveryMenRepository deliveryMenRepository ;


    @Override
    public DeliveryMen AddDeliveryMen(DeliveryMen deliveryMen) {
        return deliveryMenRepository.save(deliveryMen);
    }

    @Override
    public DeliveryMen GetdeliveryMenId(Long id_DeliverMen) {
        DeliveryMen deliveryMen= deliveryMenRepository.findById(id_DeliverMen).orElse(null);
        if ( deliveryMen != null) {
            return deliveryMen;
        }
        return  null ;
    }

    @Override
    public List<DeliveryMen> GetAllDelivery() {
        List<DeliveryMen> deliveryMenList = deliveryMenRepository.findAll();
        if (deliveryMenList != null ) {
            return deliveryMenList;
        }
        return null ;
    }
}
