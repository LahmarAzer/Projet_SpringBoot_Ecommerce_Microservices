package tn.esprit.claimfacturesservice.Controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.claimfacturesservice.Entities.Facture;
import tn.esprit.claimfacturesservice.Service.FacturAvoirService;
import tn.esprit.claimfacturesservice.Service.FactureService;
import tn.esprit.claimfacturesservice.dtoEntities.FactureAvoirDto;

import java.util.List;

@RestController
@AllArgsConstructor

@RequestMapping("/claimFacture/FactureAvoir")
public class FactureAvoirController {

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









//
//    @Autowired
//    tn.esprit.claimfacturesservice.Service.FacturAvoirService FacturAvoirService;
//    @PostMapping()
//    public FactureAvoirDto factureAvoir(@RequestBody FactureAvoirDto factureAvoirDto) {
//        return FacturAvoirService.addFactureAvoir(factureAvoirDto);
//    }
//
//    @PutMapping()
//    public FactureAvoirDto UpdateFacture(@RequestBody FactureAvoirDto factureAvoirDto) {
//        return	FacturAvoirService.UpdateFactureAvoir(factureAvoirDto);
//    }
//    @GetMapping("/{id}")
//    public FactureAvoirDto retrieveFactureById(@PathVariable Long id ) {
//        return	FacturAvoirService.retrieveFactureAvoirById(id);
//    }
//
//    @GetMapping()
//    public List<FactureAvoirDto> retrieveAllFactureAvoir() {
//        return	FacturAvoirService.retrieveAllFactureAvoir();
//    }
//    @DeleteMapping("/{id}")
//    public Boolean DeleteFactureAvoir(@PathVariable Long id) {
//        return	FacturAvoirService.DeleteFactureAvoir(id);
//    }
//



}
