package com.acme.backendunityvolunteer.infraestructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorarioRequest {

    @NotNull(message = "El id no puede estar vacío")
    private Long userid;

    @NotNull(message = "El nombre no puede estar vacío")
    @Size(min = 5, message = "El nombre debe tener al menos 5 caracteres")
    private String nombre;

    @NotNull(message = "El numero de actividades no puede estar vacío")
    private Long num_Actividades;

    @NotNull(message = "El numero de horas no puede estar vacío")
    private Long num_Horas;
}
