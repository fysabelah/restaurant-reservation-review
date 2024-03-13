package com.restaurant.reservationreview.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = {"date"}, allowGetters = true)
public class RatingDto extends Dto {

    @NotEmpty
    @Schema(example = "George")
    private String name;

    @Min(0)
    @Max(10)
    @Schema(example = "8")
    private Integer score;

    @Schema(example = "Comida muita boa")
    private String comment;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String date;

    public RatingDto(String id, String name, Integer score, String comment, String date) {
        super(id);
        this.name = name;
        this.score = score;
        this.comment = comment;
        this.date = date;
    }
}
