package com.restaurant.reservationreview.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Data
public class Restaurant implements Serializable {
    @Id
    private String id;

    private String name;

    private String location;

    private String cuisineType;

    private Boolean active;

    private Integer averageRating;

    private Integer manytables;

}