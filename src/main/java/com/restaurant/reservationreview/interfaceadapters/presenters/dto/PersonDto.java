package com.restaurant.reservationreview.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"id"})
public class PersonDto extends Dto implements Serializable {

    @NotBlank(message = "É preciso informar um nome para contato!")
    private String name;

    @NotBlank(message = "É preciso informar um e-mail para contato!")
    private String email;

    @NotBlank(message = "É preciso informar um telefone para contato!")
    private String phone;

}
