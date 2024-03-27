package com.restaurant.reservationreview.interfaceadapters.presenters.dto;

import com.restaurant.reservationreview.util.enums.FoodType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class RestaurantDto extends Dto implements Serializable {

    @NotEmpty
    @Schema(example = "Paparoto Cucina")
    private String name;

    private AdressDto adressDto;

    private List<BusinnessHoursDto> businnessHoursDto;

    @NotEmpty
    @Schema(example = "BRAZILIAN")
    private FoodType foodType;

    @NotEmpty
    @Schema(example = "30")
    private Integer capacity;

    private BigDecimal averageRating;

    private boolean active;

    public RestaurantDto(String id, String name, AdressDto adressDto, List<BusinnessHoursDto> businnessHoursDto, FoodType foodType, Integer capacity, BigDecimal averageRating, boolean active) {
        super(id);
        this.name = name;
        this.adressDto = adressDto;
        this.businnessHoursDto = businnessHoursDto;
        this.foodType = foodType;
        this.capacity = capacity;
        this.averageRating = averageRating;
        this.active = active;
    }

    public RestaurantDto(String name, AdressDto adressDto, List<BusinnessHoursDto> businnessHoursDto, FoodType foodType, Integer capacity) {
        this.name = name;
        this.adressDto = adressDto;
        this.businnessHoursDto = businnessHoursDto;
        this.foodType = foodType;
        this.capacity = capacity;
    }

}