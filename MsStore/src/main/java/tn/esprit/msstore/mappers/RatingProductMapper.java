package tn.esprit.msstore.mappers;

import tn.esprit.msstore.Entity.RatingProduct;
import tn.esprit.msstore.dtoEntities.RatingProductDto;

public class RatingProductMapper {
    public static RatingProductDto mapProductRaitingToDto(RatingProduct ratingProduct) {

        RatingProductDto ratingProductDto = RatingProductDto.builder()
                .idRaitingProduct(ratingProduct.getIdRaitingProduct())
                .nbrEtoilesProduct(ratingProduct.getNbrEtoilesProduct())
                .reviewProduct(ratingProduct.getReviewProduct())
                .productRatingId(ratingProduct.getProduct().getIdProduct())
                //.userRaitingId(ratingProduct.getUserRating().getIdUser())
                .build();
        return ratingProductDto;
    }
    public static RatingProduct mapRatingDtoToEntity(RatingProductDto ratingProductDto){

        RatingProduct ratingProduct = RatingProduct.builder()
                .idRaitingProduct(ratingProductDto.getIdRaitingProduct())
                .nbrEtoilesProduct(ratingProductDto.getNbrEtoilesProduct())
                .reviewProduct(ratingProductDto.getReviewProduct())
                .build();
        return ratingProduct;
    }
}
