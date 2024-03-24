package com.restaurant.reservationreview.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.restaurant.reservationreview.util.enums.FoodType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = {"rating"}, allowGetters = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RestaurantDto extends Dto {

    @NotEmpty
    @Schema(example = "Paparoto")
    private String name;

    private AddressDto addressDto;

    private List<BusinnessHoursDto> businnessHoursDto;

    @NotNull
    @Schema(example = "BRAZILIAN")
    private FoodType foodType;

    @Positive
    @Schema(example = "30")
    private Integer capacity;

    @Schema(example = "02:30")
    @Pattern(regexp = "[0-9]{2}:[0-9]{2}")
    private String hourToConsiderReservation;

    @Positive
    @Schema(example = "30")
    private Integer quantityTablesAvailableReservation;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal rating;

    @Schema(description = "Define se o restaurante está ou não ativo. Restaurantes desativados não podem receber reservas")
    private boolean active;
}

