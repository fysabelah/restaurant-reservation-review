package com.restaurant.reservationreview.entities;

import com.restaurant.reservationreview.util.enums.FoodType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Document(collection = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant implements Serializable {

    @Id
    private String id;

    private String name;

    private String location;

    private FoodType foodType;

    private Boolean active;

    private BigDecimal rating;

    private Integer quantityTables;

    private List<RestaurantBusinessHours> businessHours;

    public void updateRating(BigDecimal newRating) {
        if (this.rating == null || BigDecimal.ZERO.compareTo(this.rating) == 0) {
            this.rating = newRating;
        } else {
            this.rating = (this.rating.add(newRating))
                    .divide(new BigDecimal(2), 2, RoundingMode.HALF_EVEN);
        }
    }
}