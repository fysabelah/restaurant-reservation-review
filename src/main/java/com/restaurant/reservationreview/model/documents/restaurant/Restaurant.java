package com.restaurant.reservationreview.model.documents.restaurant;

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
@NoArgsConstructor
public class Restaurant implements Serializable {

    @Id
    private String id;

    private String name;

    private Adress adress;

    private List<BusinnessHours> businnessHours;

    private FoodType foodType;

    private Integer capacity;

}
