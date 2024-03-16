package com.restaurant.reservationreview.util.dto;

import com.restaurant.reservationreview.util.enums.FoodType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class RestaurantDto extends Dto {

    private List<BusinnessHoursDto> businnessHoursDto;

    private AdressDto adressDto;

    @Schema(example = "BRAZILIAN")
    private FoodType foodType;

    @Schema(example = "30")
    private Integer capacity;

    public RestaurantDto(String id, List<BusinnessHoursDto> collect, FoodType foodType, Integer capacity) {
    }
}
