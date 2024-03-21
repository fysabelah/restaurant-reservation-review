package com.restaurant.reservationreview.util.dto;

import com.restaurant.reservationreview.util.enums.FoodType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class RestaurantDto extends Dto implements Serializable {

    private String name;

    private AdressDto adressDto;

    private List<BusinnessHoursDto> businnessHoursDto;

    @Schema(example = "BRAZILIAN")
    private FoodType foodType;

    @Schema(example = "30")
    private Integer capacity;

    public RestaurantDto(String id, String name, AdressDto adressDto, List<BusinnessHoursDto> businnessHoursDto, FoodType foodType, Integer capacity) {
        super(id);
        this.name = name;
        this.adressDto = adressDto;
        this.businnessHoursDto = businnessHoursDto;
        this.foodType = foodType;
        this.capacity = capacity;
    }

    public RestaurantDto(String name, AdressDto adressDto, List<BusinnessHoursDto> businnessHoursDto, FoodType foodType, Integer capacity) {
        this.name = name;
        this.adressDto = adressDto;
        this.businnessHoursDto = businnessHoursDto;
        this.foodType = foodType;
        this.capacity = capacity;
    }
}

