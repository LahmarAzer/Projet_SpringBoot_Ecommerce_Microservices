package tn.esprit.claimfacturesservice.Controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.claimfacturesservice.Entities.Facture;
import tn.esprit.claimfacturesservice.Service.FactureService;

import java.text.ParseException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/claimFacture/facture")
public class FactureController {

    @Autowired
    FactureService factureService;
    @PostMapping()
    public Facture addFacture(@RequestBody Facture facture) {
        return factureService.addFacture(facture);
    }


    @PutMapping()
    public Facture UpdateFacture(@RequestBody Facture facture) {
        return	factureService.UpdateFacture(facture);
    }
    @GetMapping("/{id}")
    public Facture retrieveFactureById(@PathVariable Long id ) {
        return	factureService.retrieveFactureById(id);
    }

    @GetMapping()
    public List<Facture> retrieveAllFactures() {
        return	factureService.retrieveAllFactures();
    }
    @DeleteMapping("/{id}")
    public Boolean DeleteFacture(@PathVariable Long id) {
        return	factureService.DeleteFacture(id);
    }

    @GetMapping("/bF")
    public List<Object[]> getReservationsDuringBlackFriday() throws ParseException {
        return  factureService.getFacturesDuringBlackFriday();
    }

}
