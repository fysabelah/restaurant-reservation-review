package com.restaurant.reservationreview.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.restaurant.reservationreview.util.enums.FoodType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"rating"}, allowGetters = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RestaurantDto extends Dto {

    @NotBlank(message = "O campo Nome é obrigatório")
    @Schema(example = "Restaurante Comida Gostosa é aqui")
    private String name;

    @NotBlank(message = "O campo Localização é obrigatório")
    @Schema(example = "São Paulo")
    private String location;

    @NotBlank(message = "O campo Tipo de Cozinha é obrigatório")
    @Schema(example = "BRAZILIAN")
    private FoodType foodType;

    private boolean active;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal rating;

    @NotBlank(message = "O campo Quantidade de Mesas é obrigatório")
    @Schema(example = "20")
    private Integer quantityTables;

    private List<RestaurantBusinessHoursDto> businessHoursDto;

}
