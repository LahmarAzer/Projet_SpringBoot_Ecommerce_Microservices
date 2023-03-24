package tn.esprit.msstore.Controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.msstore.Repository.ProductRepository;
import tn.esprit.msstore.Service.IProductService;
import tn.esprit.msstore.Service.IPromotionProduct;

import java.util.Date;

@AllArgsConstructor
@RestController
@RequestMapping("/store/promotion")
public class promotionController {
    IProductService iProductService;
    ProductRepository productRepository ;
    IPromotionProduct iPromotionProduct;

    @PostMapping("/sousCategorie/{sousCategorieId}/{discountPercentage}/{startDate}/{endDate}")
    public void applyPromotionForSousCategorie(@PathVariable Long sousCategorieId, @PathVariable float discountPercentage,
                                               @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                               @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        iPromotionProduct.applyPromotionForSousCategorie(sousCategorieId, discountPercentage, startDate, endDate);
    }
    /*@GetMapping("/{promotionId}")
    public PromotionProduct getPromotion(@PathVariable Long promotionId) {
        return iPromotionProduct.getPromotion(promotionId);
    }*/
    @PostMapping("/{idSouCategorie}/{idPromotion}/annuler")
    public String cancelPromotion(@PathVariable Long idSouCategorie,@PathVariable Long idPromotion ) {
        return iPromotionProduct.cancelPromotionForSousCategorie(idSouCategorie,idPromotion);
    }

}
