package com.restaurant.reservationreview.util.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class PersonDto extends Dto implements Serializable {

    @NotBlank(message = "É preciso informar um nome para contato!")
    private String name;

    @NotBlank(message = "É preciso informar um e-mail para contato!")
    private String email;

    @NotBlank(message = "É preciso informar um telefone para contato!")
    private String phone;

}
