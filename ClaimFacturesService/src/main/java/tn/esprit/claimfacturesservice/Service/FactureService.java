package tn.esprit.claimfacturesservice.Service;

import tn.esprit.claimfacturesservice.Entities.Facture;

import java.text.ParseException;
import java.util.List;

public interface FactureService {
    public Facture addFacture(Facture facture);
    public Facture UpdateFacture(Facture facture);
    public Boolean DeleteFacture(Long id);
    public Facture retrieveFactureById(Long id);
    public List<Facture> retrieveAllFactures();
    public List<Object[]> getFacturesDuringBlackFriday() throws ParseException;

    public void retrieveAndUpdateStatusFacture();
   // public void archiveExpiredFacture();
    public Double convertirMontant(Double montant, String deviseOrigine, String deviseDestination);
}
