package tn.esprit.msstore.dtoEntities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link tn.esprit.msstore.Entity.RatingProduct} entity
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
public class RatingProductDto implements Serializable {
    private final Long idRaitingProduct;
    private final int nbrEtoilesProduct;
    private final String reviewProduct;
    private final Long productRatingId;
    private final Long userRaitingId;
}
