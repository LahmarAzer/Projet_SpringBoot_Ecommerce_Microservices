package tn.esprit.msstore.Service;

import java.util.Date;

public interface IPromotionProduct {
    public void applyPromotionForSousCategorie(Long sousCategorieId, float discountPercentage, Date startDate, Date endDate);
    public  void checkPromotionsExpiration();
    public String cancelPromotionForSousCategorie(Long sousCategorieId, Long idPromotion);
}
