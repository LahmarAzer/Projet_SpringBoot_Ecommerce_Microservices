package tn.esprit.msstore.Service;

import tn.esprit.msstore.dtoEntities.RatingProductDto;

public interface IRatingProduct {
    Object addProductRating(RatingProductDto ratingProductDto , Long productId,String userId);
    public RatingProductDto modifierRating(RatingProductDto ratingProductDto, Long ratingId, String userId);
    Double getProductRating(Long productId);
}
