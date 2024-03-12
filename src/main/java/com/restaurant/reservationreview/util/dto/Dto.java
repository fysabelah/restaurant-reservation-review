package com.restaurant.reservationreview.util.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dto {
    @Schema(example = "código do indicador da tabela")
    private String id;
}