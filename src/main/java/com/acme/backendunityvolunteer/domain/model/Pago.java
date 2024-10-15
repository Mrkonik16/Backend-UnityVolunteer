package com.acme.backendunityvolunteer.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long organizacionId;
    private Float monto;
    @Column(nullable = false)
    private TipoSubscricion tipoSubscricion;

}
