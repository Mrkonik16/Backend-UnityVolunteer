package com.acme.backendunityvolunteer.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "logros")
public class Logros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLogro;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String criterio;
}
