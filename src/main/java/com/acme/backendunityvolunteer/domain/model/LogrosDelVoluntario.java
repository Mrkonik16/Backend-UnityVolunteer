package com.acme.backendunityvolunteer.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "logros_del_voluntario")
public class LogrosDelVoluntario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_voluntario", nullable = false)
    private PerfilVoluntario voluntario;

    @ManyToOne
    @JoinColumn(name = "id_logro", nullable = false)
    private Logros logro;

    @Column(nullable = false)
    private LocalDate fechaObtencion;
}