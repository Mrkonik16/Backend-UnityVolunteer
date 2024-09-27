package com.acme.backendunityvolunteer.infraestructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagoRequest {

    @NotNull(message = "El monto no puede estar vacío")
    private Float monto;

    @NotNull(message = "El ID de la organización no puede estar vacío")
    private Long organization_id;


    @NotNull(message = "El tipo de usuario no puede estar vacío")
    private String tipoSubscripcion;




}
