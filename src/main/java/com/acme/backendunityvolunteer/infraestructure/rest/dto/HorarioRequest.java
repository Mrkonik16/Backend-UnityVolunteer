package com.acme.backendunityvolunteer.infraestructure.rest.dto;

import com.acme.backendunityvolunteer.domain.model.Usuario;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorarioRequest {

    @NotNull(message = "El horario debe pertenecerle a un usuario")
    private Long usuarioId;

    @NotNull(message = "El nombre del horario no puede estar vacío")
    @Size(min = 2, message = "El nombre del horario debe tener al menos 2 caracteres")
    private String nombreHorario;

    @NotNull(message = "El número de actividades no puede estar vacío")
    private Long num_Actividades;

    @NotNull(message = "El número de horas no puede estar vacío")
    private Long num_Horas;
}
