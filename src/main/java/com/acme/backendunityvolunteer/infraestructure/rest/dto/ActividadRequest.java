package com.acme.backendunityvolunteer.infraestructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActividadRequest {

    @NotNull(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "El tipo no puede estar vacío")
    private String tipo;

    @NotNull(message = "La duracion no puede estar vacío")
    private Float duracion;

}
