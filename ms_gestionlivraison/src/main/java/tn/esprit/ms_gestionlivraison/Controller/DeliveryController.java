package tn.esprit.ms_gestionlivraison.Controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.ms_gestionlivraison.DTOEntities.DeliveryDTO;
import tn.esprit.ms_gestionlivraison.Entities.Delivery;
import tn.esprit.ms_gestionlivraison.Entities.DeliveryMen;
import tn.esprit.ms_gestionlivraison.Entities.MeanOfTransport;
import tn.esprit.ms_gestionlivraison.Entities.StatusDelivery;
import tn.esprit.ms_gestionlivraison.Service.DeliveryIServiceIMP;
import tn.esprit.ms_gestionlivraison.Service.DeliveryMenIServiceIMP;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/delivery")
@AllArgsConstructor
public class DeliveryController {
    DeliveryIServiceIMP deliveryIServiceIMP;
    DeliveryMenIServiceIMP deliveryMenIServiceIMP ;

   @PostMapping("/adddelivery")
   public DeliveryDTO addDelivery(@RequestBody DeliveryDTO deliveryDTO) {
       try {
           return deliveryIServiceIMP.addDelivery(deliveryDTO);
       } catch (IllegalArgumentException ex) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
       }
   }


    @PutMapping("/updatedelivery")
    public DeliveryDTO updateDelivery(@RequestBody DeliveryDTO deliveryDTO) {
        try {
            return deliveryIServiceIMP.updateDelivery(deliveryDTO);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }


    @GetMapping("/getdeliverybyid/{idLivraison}")
    public DeliveryDTO getDeliveryId(@PathVariable Long idLivraison){
        return deliveryIServiceIMP.getDeliveryId(idLivraison); }

    @GetMapping("/retrievealldelivery")
    public List<DeliveryDTO> getAllDelivery(){
        return deliveryIServiceIMP.getAllDelivery(); }


    @PutMapping("/assigndeliverytodriver/{deliveryId}")
    public Delivery assignDeliveryToDriverByAvailabilty(@PathVariable Long deliveryId){
        return deliveryIServiceIMP.assignDeliveryToDriverByAvailabilty(deliveryId);
    }
    @PutMapping("/assigncarttodelivery/{id_Delivery}/{idCart}")
    public Delivery AssignCartToDelivery(@PathVariable Long id_Delivery,@PathVariable Long idCart){
        return deliveryIServiceIMP.AssignCartToDelivery(id_Delivery,idCart);
    }
    @PutMapping("/assignDeliveryToBestShippingCompany")
    void assignDeliveryToBestShippingCompany(@RequestBody Delivery delivery){
        deliveryIServiceIMP.assignDeliveryToBestShippingCompany(delivery);
    }
    @PutMapping("/updatedeliverystatus/{id_Delivery}/{newStatus}")
    void updateDeliveryStatus(@PathVariable long id_Delivery, @PathVariable StatusDelivery newStatus){
        deliveryIServiceIMP.updateDeliveryStatus(id_Delivery,newStatus);
    }
    @PutMapping("/unassigndeliverymanoncancelled/{id_Delivery}")
    void unassignDeliveryManOnCancelled(@PathVariable Long id_Delivery){
        deliveryIServiceIMP.unassignDeliveryManOnCancelled(id_Delivery);
    }
    @PutMapping("/assigndeliverytodriverbylocation/{idDelivery}")
     void assignDeliveryToDeliveryMan(@PathVariable Long idDelivery){
        deliveryIServiceIMP.assignDeliveryToDeliveryMan(idDelivery);
    }
    @PutMapping("/assignmeanoftransport/{idDelivery}")
    MeanOfTransport assignMeansOfTransport(@PathVariable Long idDelivery){
        return deliveryIServiceIMP.assignMeansOfTransport(idDelivery);
    }
   @PutMapping("/updatedeliverymenscore/{id_DeliverMen}/{score}")
    public void updateDeliveryManScore(@PathVariable Long id_DeliverMen, @PathVariable int score){
       deliveryIServiceIMP.updateDeliveryManScore(id_DeliverMen,score);
   }
    @GetMapping("/reports/deliveries-per-day/{startDate}/{endDate}")
    public ResponseEntity<List<Map<String, Object>>> getDeliveriesPerDayReport(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date startDate,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date endDate) {
        List<Map<String, Object>> report = deliveryIServiceIMP.getDeliveriesPerDay(   startDate,  endDate);
        return ResponseEntity.ok(report);
    }
    @PostMapping("/adddeliverymen")
    public DeliveryMen AddDeliveryMen(@RequestBody DeliveryMen deliveryMen) {
        return deliveryMenIServiceIMP.AddDeliveryMen(deliveryMen);
    }

    @GetMapping("/getdeliverymenid/{id_DeliverMen}")
    public DeliveryMen GetdeliveryMenId(@PathVariable Long id_DeliverMen) {
        return  deliveryMenIServiceIMP.GetdeliveryMenId(id_DeliverMen);
    }
    @RolesAllowed("admin")
    @GetMapping("/getalldeliverymen")
    public List<DeliveryMen> GetAllDelivery(){
        return deliveryMenIServiceIMP.GetAllDelivery();

    }
    @DeleteMapping("/deletedelivery/{idLivraison}")
    public void deleteById(@PathVariable Long idLivraison){
         deliveryIServiceIMP.removeDelivery(idLivraison); }
}
