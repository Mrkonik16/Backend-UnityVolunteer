package com.acme.backendunityvolunteer.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorarioDTO {
    private Long id;
    private Long userid;
    private String nombre;
    private Long num_Actividades;
    private Long num_Horas;
}
