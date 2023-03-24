package tn.esprit.ms_gestionlivraison.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ms_gestionlivraison.Entities.Adress;

public interface AdressRepository extends JpaRepository<Adress,Long> {
}
