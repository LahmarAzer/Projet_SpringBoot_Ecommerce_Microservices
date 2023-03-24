package tn.esprit.msstore.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.msstore.Entity.Product;
import tn.esprit.msstore.Entity.PromotionProduct;
import tn.esprit.msstore.Entity.Sous_CategoryProduct;
import tn.esprit.msstore.Repository.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class IPromotionProductIMP implements IPromotionProduct{
    UserRepository userRepository;
    FavorisProductRepository favorisProductRepository;
    ProductRepository productRepository;
    CartLineRepository cartLineRepository ;
    SousCategorieRepository sousCategorieRepository ;
    PromotionProductRepository promotionProductRepository;


    @Override
    public void applyPromotionForSousCategorie(Long sousCategorieId, float discountPercentage, Date startDate, Date endDate) {
        // Trouver la sous-catégorie de produits pour laquelle appliquer la promotion
        Sous_CategoryProduct sousCategorie = sousCategorieRepository.findById(sousCategorieId)
                .orElseThrow(() -> new IllegalArgumentException("Sous-catégorie de produits non trouvée"));

        // Trouver tous les produits de la sous-catégorie
        List<Product> products = sousCategorie.getProductListSousCategories();

        // Vérifier que la promotion est en cours
        Date currentDate = new Date();
        if (currentDate.before(startDate) || currentDate.after(endDate)) {
            throw new IllegalArgumentException("La promotion n'est pas en cours");
        }

        // Appliquer la remise à chaque produit de la sous-catégorie
        for (Product product : products) {
            float oldPrice = product.getPriceProduct();
            float newPrice = oldPrice * (1 - discountPercentage);
            product.setPriceProduct(newPrice);
            product.setDiscountProduct(discountPercentage);
            productRepository.save(product);
        }
        // Enregistrer la promotion pour la sous-catégorie
        PromotionProduct promotion = new PromotionProduct();
        promotion.setDiscount(discountPercentage);
        promotion.setStartDate(startDate);
        promotion.setEndDate(endDate);
        promotion.setSousCategoriePromotion(sousCategorie);
        promotionProductRepository.save(promotion);
    }
    @Override
    public String cancelPromotionForSousCategorie(Long sousCategorieId, Long idPromotion) {
        // Trouver la sous-catégorie de produits pour laquelle annuler la promotion
        Sous_CategoryProduct sousCategorie = sousCategorieRepository.findById(sousCategorieId).orElse(null);

        PromotionProduct promotion = promotionProductRepository.findById(idPromotion)
                .orElse(null);
        if (sousCategorie != null && promotion != null) {
            // Vérifier que la promotion correspond à la sous-catégorie donnée
            if (!promotion.getSousCategoriePromotion().equals(sousCategorie)) {
                return ("La promotion ne correspond pas à la sous-catégorie donnée");
            }
            Date currentDate = new Date();
            if (currentDate.after(promotion.getEndDate())) {
                return ("La promotion est déjà terminée");
            }
            // Réinitialiser le prix de chaque produit de la sous-catégorie
            List<Product> products = sousCategorie.getProductListSousCategories();
            for (Product product : products) {
                float oldPrice = product.getPriceProduct();
                float discountPercentage = product.getDiscountProduct();
                float newPrice = oldPrice / (1 - discountPercentage);
                product.setPriceProduct(newPrice);
                product.setDiscountProduct(0);
                productRepository.save(product);
            }
            // Supprimer la promotion
            promotionProductRepository.delete(promotion);
            return ("promotion canceled");
        }
        return ("sous_category or promotion not found");
    }
    @Override
    //@Scheduled(fixedDelay = 86400000) // Exécution tous les jours
    @Scheduled(fixedDelay = 30000) // Exécution tous les jours
    public void checkPromotionsExpiration() {
        // Trouver toutes les promotions enregistrées en base de données
        List<PromotionProduct> promotions = promotionProductRepository.findAll();

        // Vérifier si la date de fin de chaque promotion est dépassée
        Date currentDate = new Date();
        for (PromotionProduct promotion : promotions) {
            if (currentDate.after(promotion.getEndDate())) {
                // Annuler la promotion
                cancelPromotionForSousCategorie(promotion.getSousCategoriePromotion().getId(), promotion.getId());
            }
        }
    }
}
