package com.acme.backendunityvolunteer.infraestructure.rest.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthResponse {
    private String token;
    private String tipoUsuario;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
