package com.acme.backendunityvolunteer.application.dto;

import com.acme.backendunityvolunteer.domain.model.TipoSubscricion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizacionSuscripcionDTO {
    private Long organizacionId;
    private TipoSubscricion subscripcion;

}
