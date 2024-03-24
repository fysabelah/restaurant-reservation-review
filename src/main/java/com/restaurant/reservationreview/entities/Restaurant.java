package com.restaurant.reservationreview.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.restaurant.reservationreview.util.enums.FoodType;

import java.io.Serializable;
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

    private List<BusinnessHours> businnessHours;

    private FoodType foodType;

    private Integer capacity;

    public Restaurant(String restaurantId) {
    }
}
