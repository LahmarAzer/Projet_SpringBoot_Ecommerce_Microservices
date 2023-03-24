package tn.esprit.ms_gestionlivraison.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.ms_gestionlivraison.Entities.DeliveryMen;

import java.util.List;

public interface DeliveryMenRepository extends JpaRepository<DeliveryMen,Long> {
     @Query(value = "SELECT * FROM delivery_men WHERE availability = true AND " +
             "ST_Distance_Sphere(location, ST_GeomFromText(:point)) <= :distance",
             nativeQuery = true)
     List<DeliveryMen> findByAvailabilityAndLocationWithin(@Param("point") String point,
                                                           @Param("distance") double distance);
     List<DeliveryMen> findByAvailabilityIsTrue();
}
