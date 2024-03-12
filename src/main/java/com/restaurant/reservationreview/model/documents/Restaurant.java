package com.restaurant.reservationreview.model.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "restaurant")
@Data
public class Restaurant implements Serializable {
    @Id
    private String id;

    private String name;

    private String location;

    private String cuisineType;

    private Boolean active;

    private Integer averageRating;

    private Integer quantityTables;

    private List<RestaurantBusinessHours> businessHours;

}