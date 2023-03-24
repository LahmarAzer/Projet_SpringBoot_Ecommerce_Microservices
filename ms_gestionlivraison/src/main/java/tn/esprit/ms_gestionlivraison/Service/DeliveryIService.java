package tn.esprit.ms_gestionlivraison.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ms_gestionlivraison.DTOEntities.DeliveryDTO;
import tn.esprit.ms_gestionlivraison.Entities.*;
import tn.esprit.ms_gestionlivraison.Mappers.DeliveryMappers;
import tn.esprit.ms_gestionlivraison.Repositories.CartRepository;
import tn.esprit.ms_gestionlivraison.Repositories.DeliveryCompanyRepository;
import tn.esprit.ms_gestionlivraison.Repositories.DeliveryMenRepository;
import tn.esprit.ms_gestionlivraison.Repositories.DeliveryRepository;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
@Slf4j
@Service
@AllArgsConstructor
public class DeliveryIService implements DeliveryIServiceIMP {
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    DeliveryMenRepository deliveryMenRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    DeliveryCompanyRepository deliveryCompanyRepository;


    @Override
    public DeliveryDTO addDelivery(DeliveryDTO deliveryDTO) throws IllegalArgumentException {
        if (deliveryDTO.getDeliveryPrice() <= 0) {
            throw new IllegalArgumentException("Delivery price must be greater than zero.");
        }
        if (deliveryDTO.getDeliverytime().after(new java.util.Date())) {
            throw new IllegalArgumentException("Delivery time must be before or equal to current date.");
        }
        if (deliveryDTO.getExpectedDeliveryDate().before(new java.util.Date())) {
            throw new IllegalArgumentException("Expected delivery date must be after current date.");
        }
        if (deliveryDTO.getStatusDelivery() == null) {
            throw new IllegalArgumentException("Delivery status must be specified.");

        }
        if (deliveryDTO.getWeight() <= 0) {
            throw new IllegalArgumentException("Delivery weight must be greater than zero.");
        }
        if (deliveryDTO.getCustomerSatisfaction() < 0 || deliveryDTO.getCustomerSatisfaction() > 10) {
            throw new IllegalArgumentException("Customer satisfaction must be between 0 and 10.");
        }

        Delivery delivery = deliveryRepository.save(DeliveryMappers.mapToEntity(deliveryDTO));
        return DeliveryMappers.mapToDto(delivery);
    }



    @Override
    public DeliveryDTO updateDelivery(DeliveryDTO deliveryDTO) throws IllegalArgumentException {
        if (deliveryDTO.getDeliveryPrice() <= 0) {
            throw new IllegalArgumentException("Delivery price must be greater than zero.");
        }
        if (deliveryDTO.getDeliverytime().after(new java.util.Date())) {
            throw new IllegalArgumentException("Delivery time must be before or equal to current date.");
        }
        if (deliveryDTO.getExpectedDeliveryDate().before(new java.util.Date())) {
            throw new IllegalArgumentException("Expected delivery date must be after current date.");
        }
        if (deliveryDTO.getStatusDelivery() == null) {
            throw new IllegalArgumentException("Delivery status must be specified.");

        }
        if (deliveryDTO.getWeight() <= 0) {
            throw new IllegalArgumentException("Delivery weight must be greater than zero.");
        }
        if (deliveryDTO.getCustomerSatisfaction() < 0 || deliveryDTO.getCustomerSatisfaction() > 10) {
            throw new IllegalArgumentException("Customer satisfaction must be between 0 and 10.");
        }
        Delivery delivery = deliveryRepository.save(DeliveryMappers.mapToEntity(deliveryDTO));

        return DeliveryMappers.mapToDto(delivery);

    }

    @Override
    public DeliveryDTO getDeliveryId(Long idLivraison) {
        Delivery delivery = deliveryRepository.findById(idLivraison).orElse(null);
        if (delivery != null) {
            return DeliveryMappers.mapToDto(delivery);
        }
        return null;
    }

    @Override
    public List<DeliveryDTO> getAllDelivery() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        if (deliveries != null ) {
            return deliveries.stream().
                    map(livraison -> DeliveryMappers.mapToDto(livraison))
                    .collect(Collectors.toList());
        }
        return  null;
    }

    @Override
    public void removeDelivery(Long id_Delivery) {
        Delivery delivery = deliveryRepository.findById(id_Delivery).orElse(null);
        if (delivery != null) {
            deliveryRepository.deleteById(id_Delivery);
        }
        log.info("delivery n'existe pas ");
    }


    @Transactional
    @Override

    public void assignDeliveryToDeliveryMan(Long idDelivery) {
        // Recherche de tous les livreurs disponibles
        List<DeliveryMen> availableDeliveryMen = deliveryMenRepository.findByAvailabilityIsTrue();
        Delivery deliveryaafct = deliveryRepository.findById(idDelivery).orElse(null);

        if (availableDeliveryMen != null && deliveryaafct != null) {

            // Sélection du livreur disponible le plus proche de l'adresse de livraison
            double shortestDistance = Double.MAX_VALUE;
            DeliveryMen closestDeliveryMan = null;
            for (DeliveryMen deliveryMan : availableDeliveryMen) {
                Adress deliveryManAddress = deliveryMan.getAdresslivreur();
                Adress deliveryAddress = deliveryaafct.getAdress();
                double distance = calculateDistance(deliveryManAddress.getLatitude(), deliveryManAddress.getLongitude(), deliveryAddress.getLatitude(), deliveryAddress.getLongitude());
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    closestDeliveryMan = deliveryMan;
                }
            }

            // Si aucun livreur n'est disponible, on ne fait rien
            if (closestDeliveryMan == null) {
                return;
            }
            // Affectation de la livraison au livreur
            deliveryaafct.setDeliveryMen(closestDeliveryMan);
            deliveryRepository.save(deliveryaafct);
            deliveryMenRepository.save(closestDeliveryMan);
        }
        log.info("error se produit lors d'affectation de livreur");
    }
    @Transactional
    @Override
    public MeanOfTransport assignMeansOfTransport(Long idDelivery) {
        Delivery delivery = deliveryRepository.findById(idDelivery).orElse(null);
        Adress deliveryAddress = delivery.getAdress();
        double weight = delivery.getWeight();

        // Calcul de la distance à parcourir pour la livraison
        double distance = calculateDistance(deliveryAddress.getLatitude(), deliveryAddress.getLongitude(), delivery.getDeliveryMen().getAdresslivreur().getLatitude(), delivery.getDeliveryMen().getAdresslivreur().getLongitude());

        // Choix du moyen de transport en fonction de la distance et du poids
        if (distance <= 1000 && weight <= 500) {
            delivery.setMeanOfTransport(MeanOfTransport.TRUCKERS);
            return MeanOfTransport.TRUCKERS;
        } else if (distance <= 5000 && weight <= 2000) {
            delivery.setMeanOfTransport(MeanOfTransport.RAILWAYS);
            return MeanOfTransport.RAILWAYS;
        } else if (distance <= 10000 && weight <= 5000) {
            delivery.setMeanOfTransport(MeanOfTransport.MARITIMERS);
            return MeanOfTransport.MARITIMERS;
        } else if (distance > 10000 && weight <= 10000) {
            delivery.setMeanOfTransport(MeanOfTransport.AIRPLANES);
            return MeanOfTransport.AIRPLANES;
        } else {
            return null; // aucun moyen de transport ne convient
        }
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        return distance;
    }


    @Transactional
    @Override
    public Delivery assignDeliveryToDriverByAvailabilty(Long deliveryId) {
        List<DeliveryMen> availableDrivers = deliveryMenRepository.findByAvailabilityIsTrue();

        if (availableDrivers != null ) {

            DeliveryMen driver = availableDrivers.get(0);
            Delivery deliveryaafct = deliveryRepository.findById(deliveryId).orElse(null);
            if (deliveryaafct != null) {
                deliveryaafct.setDeliveryMen(driver);
                driver.setAvailability(false);

                deliveryMenRepository.save(driver);
                deliveryRepository.save(deliveryaafct);


            }
            return deliveryaafct;
        }
        return null ;


    }

    @Transactional
    @Override
    public Delivery AssignCartToDelivery(Long id_Delivery, Long idCart) {
        Cart cart = cartRepository.findById(idCart).orElse(null);
        Delivery delivery = deliveryRepository.findById(id_Delivery).orElse(null);
        if (cart != null && delivery != null) {
            delivery.setCart(cart);
        }
        deliveryRepository.save(delivery);
        return delivery;
    }

    @Transactional
    @Override
    public void assignDeliveryToBestShippingCompany(Delivery delivery) {
        List<DeliveryCompany> deliveryCompanies = deliveryCompanyRepository.findAll();
        DeliveryCompany bestShippingCompany = null;
        double bestShippingCompanyPerformance = 0.0;

        // Trouver la société de livraison avec la meilleure performance
        for (DeliveryCompany deliveryCompany : deliveryCompanies) {
            double shippingCompanyPerformance = calculateShippingCompanyPerformance(deliveryCompany);
            if (shippingCompanyPerformance > bestShippingCompanyPerformance) {
                bestShippingCompany = deliveryCompany;
                bestShippingCompanyPerformance = shippingCompanyPerformance;
            }
        }

        // Assigner la livraison à la société de livraison choisie
        if (bestShippingCompany != null) {
            List<Delivery> deliveries = deliveryRepository.findByDeliveryCompany(bestShippingCompany);
            List<Delivery> modifiableDeliveries = new ArrayList<>(deliveries);
            delivery.setDeliveryCompany(bestShippingCompany);
            deliveryRepository.save(delivery);
        }
    }



    private double calculateShippingCompanyPerformance(DeliveryCompany deliveryCompany) {
        // Calculer la performance de la société de livraison en utilisant des KPI comme le taux de livraison à temps ou la satisfaction client
        double onTimeDeliveryRate = calculateOnTimeDeliveryRate(deliveryCompany);
        double customerSatisfaction = calculateCustomerSatisfaction(deliveryCompany);
        double shippingCompanyPerformance = (onTimeDeliveryRate + customerSatisfaction) / 2.0;
        return shippingCompanyPerformance;
    }

    private double calculateOnTimeDeliveryRate(DeliveryCompany deliveryCompany) {
        // Calculer le taux de livraison à temps de la société de livraison
        List<Delivery> deliveries = new ArrayList<>(deliveryRepository.findByDeliveryCompany(deliveryCompany)) ;
        int onTimeDeliveries = 0;
        int totalDeliveries = deliveries.size();

        for (Delivery delivery : deliveries) {
            if (delivery.getStatusDelivery() == StatusDelivery.DELIVERED && convertSqlDateToUtilDate((Date) delivery.getDeliverytime()).before(convertSqlDateToUtilDate((Date)delivery.getExpectedDeliveryDate()))) {
                onTimeDeliveries++;
            }

        }

        double onTimeDeliveryRate = (double) onTimeDeliveries / (double) totalDeliveries;

        return onTimeDeliveryRate ;
    }

    private static java.sql.Date convertSqlDateToUtilDate(java.sql.Date sqlDate) {
        return new java.sql.Date(sqlDate.getTime());
    }


    private double calculateCustomerSatisfaction(DeliveryCompany deliveryCompany) {
        // Calculer la satisfaction client de la société de livraison
        List<Delivery> deliveries = new ArrayList<>(deliveryRepository.findByDeliveryCompany(deliveryCompany));
        int satisfiedCustomers = 0;
        int totalCustomers = deliveries.size();

        for (Delivery delivery : deliveries) {
            if (delivery.getCustomerSatisfaction() >= 4) {
                satisfiedCustomers++;
            }
        }

        double customerSatisfaction = (double) satisfiedCustomers / (double) totalCustomers;
        return customerSatisfaction;
    }
    @Override
    public void updateDeliveryStatus(long id_Delivery, StatusDelivery newStatus) {
        if (newStatus != null) {
            Delivery delivery = deliveryRepository.findById(id_Delivery).orElse(null);
            delivery.setStatusDelivery(newStatus);
            deliveryRepository.save(delivery);
        } else {
            throw new IllegalArgumentException("New delivery status cannot be null.");
        }
    }
    @Override
    public void unassignDeliveryManOnCancelled(Long id_Delivery) {
        Delivery delivery =deliveryRepository.findById(id_Delivery).orElse(null);
        if (delivery != null) {
            if (delivery.getStatusDelivery() == StatusDelivery.CANCELLED && delivery.getDeliveryMen() != null) {
                DeliveryMen deliveryMan = delivery.getDeliveryMen();
                delivery.setDeliveryMen(null);
                deliveryMan.setDeliveries(null);
                deliveryMan.setAvailability(true);
                deliveryRepository.save(delivery);
                deliveryMenRepository.save(deliveryMan);
            }
        }
        log.info("delivery n'existe pas ");
    }

    @Override
    public void updateDeliveryManScore(Long id_DeliverMen, int score) {
        DeliveryMen deliveryMan =deliveryMenRepository.findById(id_DeliverMen).orElse(null);
        int currentScore = deliveryMan.getScore();
        int newScore = currentScore + score;
        deliveryMan.setScore(newScore);

        // Mettre à jour le niveau du DeliveryMan en fonction de son score
        if (newScore >= 0 && newScore < 50) {
            deliveryMan.setLevel(Level.BRONZE);
        } else if (newScore >= 50 && newScore < 100) {
            deliveryMan.setLevel(Level.SILVER);
        } else if (newScore >= 100 && newScore < 150) {
            deliveryMan.setLevel(Level.GOLD);
        } else {
            deliveryMan.setLevel(Level.PLATINUM);
        }

        deliveryMenRepository.save(deliveryMan);
    }


    @Override
    @Transactional

    public List<Map<String, Object>> getDeliveriesPerDay(java.util.Date startDate, java.util.Date endDate) {
        List<Delivery> deliveries = deliveryRepository.findByDeliverytimeBetween(startDate, endDate);
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        Map<java.util.Date, Long> deliveriesPerDay = deliveries.stream()
                .collect(Collectors.groupingBy(Delivery::getDeliverytime, Collectors.counting()));
        List<Map<String, Object>> report = new ArrayList<>();
        deliveriesPerDay.forEach((date, count) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("date", new java.sql.Date(date.getTime()));
            map.put("count", count);
            report.add(map);
        });
        return report;
    }

}












