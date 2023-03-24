package tn.esprit.msstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.msstore.Service.IRatingProduct;
import tn.esprit.msstore.dtoEntities.RatingProductDto;

//@AllArgsConstructor
@RestController
@RequestMapping("/store/raitingProduct")
public class RatingProductController {
    @Autowired
    IRatingProduct ratingProduct ;

    @PostMapping("/addRatingProduct/{productId}/{userId}")
     public RatingProductDto addProductRating(@RequestBody RatingProductDto ratingProductDto,
                                              @PathVariable Long productId , @PathVariable String userId) {
        return (RatingProductDto) ratingProduct.addProductRating(ratingProductDto, productId , userId);

    }
    @PutMapping("/updaterating/{ratingId}/{userId}")
    public RatingProductDto updaterating(@RequestBody RatingProductDto ratingProductDto,
                                             @PathVariable Long ratingId , @PathVariable String userId) {
        return (RatingProductDto) ratingProduct.addProductRating(ratingProductDto, ratingId , userId);

    }

   @GetMapping("/products/{productId}/rating")
    public ResponseEntity<Double> getProductRating(@PathVariable Long productId) {
        Double rating = ratingProduct.getProductRating(productId);
        return ResponseEntity.ok(rating);
    }

}
