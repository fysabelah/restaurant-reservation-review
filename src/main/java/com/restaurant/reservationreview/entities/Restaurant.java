package com.restaurant.reservationreview.entities;

import com.restaurant.reservationreview.util.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.restaurant.reservationreview.util.enums.FoodType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Document("restaurants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant implements Serializable {

    @Id
    private String id;

    private String name;

    private Adress adress;

    private List<BusinessHours> businessHours;

    private FoodType foodType;

    private Integer capacity;

    private BigDecimal averageRating;

    private boolean active;

    public void updateRating(BigDecimal newRating) {
        if (this.averageRating == null || BigDecimal.ZERO.compareTo(this.averageRating) == 0) {
            this.averageRating = newRating;
        } else {
            this.averageRating = (this.averageRating.add(newRating))
                    .divide(new BigDecimal(2), 2, RoundingMode.HALF_EVEN);
        }
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0003"));
        }

        this.name = name;
    }

    public void setLocation(Adress adress) {
        if (adress == null || adress.getCity().trim().isEmpty()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0201"));
        }

        this.adress = adress;
    }

    public void setFoodType(FoodType foodType) {
        if (foodType == null) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0202"));
        }

        this.foodType = foodType;
    }

}