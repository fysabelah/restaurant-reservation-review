package com.restaurant.reservationreview.interfaceadapters.presenters.dto;

import com.restaurant.reservationreview.util.enums.FoodType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RestaurantDto extends Dto {

    public RestaurantDto(String id) {
        super(id);
    }

    @NotBlank(message = "O campo Nome é obrigatório")
    @Schema(example = "Restaurante Comida Gostosa é aqui")
    private String name;

    @NotBlank(message = "O campo Localização é obrigatório")
    @Schema(example = "São Paulo")
    private String location;

    @NotBlank(message = "O campo Tipo de Cozinha é obrigatório")
    @Schema(example = "BRAZILIAN")
    private FoodType foodType;

    private Boolean active;

    private Integer averageRating;

    @NotBlank(message = "O campo Quantidade de Mesas é obrigatório")
    @Schema(example = "20")
    private Integer quantityTables;

    private List<RestaurantBusinessHoursDto> businessHoursDto;

}
