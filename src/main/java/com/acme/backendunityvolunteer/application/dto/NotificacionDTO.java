package com.acme.backendunityvolunteer.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotificacionDTO {
    private Long id;
    private Long usuarioId;
    //private Long actividadId;
    private String titulo;
    private String descripcion;
    private Date fecha_envio;
    private Date fecha_visto;
}
