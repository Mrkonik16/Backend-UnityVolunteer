package com.acme.backendunityvolunteer.infraestructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VolunteerAssignationToActivityRequest {

    @NotNull(message = "Se debe insertar el id de la organización")
    private Long organizacionId;

    @NotNull(message = "Se debe insertar el id del voluntario")
    private Long voluntarioId;

    @NotNull(message = "Se debe insertar el id de la actividad")
    private Long actividadId;
}
