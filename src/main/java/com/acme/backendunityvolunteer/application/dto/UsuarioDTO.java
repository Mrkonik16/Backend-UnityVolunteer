package com.acme.backendunityvolunteer.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String correo;
    private String nombre;
    private String apellido;
    private String telefono;
    private String tipoUsuario;
    private String contrasenia;


}
