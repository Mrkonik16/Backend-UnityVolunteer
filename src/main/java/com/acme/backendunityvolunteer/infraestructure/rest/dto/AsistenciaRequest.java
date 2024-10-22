package com.acme.backendunityvolunteer.infraestructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AsistenciaRequest {
    @NotNull(message = "El id del usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El id de la actividad es obligatorio")
    private Long actividadId;

    @NotNull(message = "El estado es obligatorio")
    private String estado;
}
