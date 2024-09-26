package com.acme.backendunityvolunteer.infraestructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotificacionRequest {

    @NotNull(message = "La notificacion debe pertenecerle a un usuario")
    private Long usuarioId;

    //@NotNull(message = "La notificacion debe estar relacionada a alguna actividad)
    //private Long actividadId;

    @NotNull(message = "El titulo de la notificacion no puede estar vacío")
    private String titulo;

    @NotNull(message = "La descripcion de la notificacion no puede estar vacía")
    private String descripcion;

    @NotNull(message = "La fecha de envio de la notificacion no puede estar vacía")
    private Date fecha_envio;

    @NotNull(message = "La fecha de visto de la notificacion no puede estar vacía")
    private Date fecha_visto;
}
