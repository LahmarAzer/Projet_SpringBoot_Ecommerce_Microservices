package tn.esprit.claimfacturesservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.claimfacturesservice.Entities.Facture;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface FactureRepository extends JpaRepository<Facture,Long> {
   Facture findByReference(String refence);
   //String findByCartDelivery_StatusDelivery();
   List<Facture> findByArchiveFalseAndDatefacture(LocalDate datefact);
   List<Facture> findByArchiveFalse();

   @Query("SELECT f FROM Facture f WHERE f.datefacture BETWEEN :startDate AND :endDate")
   List<Facture> findFacturesBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);



}
