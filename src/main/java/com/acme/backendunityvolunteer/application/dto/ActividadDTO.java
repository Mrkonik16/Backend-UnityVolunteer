package com.acme.backendunityvolunteer.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActividadDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private Float duracion;
}
