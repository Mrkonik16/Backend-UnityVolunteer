package com.acme.backendunityvolunteer.application.dto;

import com.acme.backendunityvolunteer.domain.model.TipoSubscricion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagoDTO {
    private Long id;
    private Float monto; ;
    private Long organizationId;
    private TipoSubscricion tipoSubscricion;
}
