package com.acme.backendunityvolunteer.domain.model.repository;

import com.acme.backendunityvolunteer.domain.model.OrganizacionSuscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizacionSuscripcionRepository extends JpaRepository<OrganizacionSuscripcion, Long> {
    

    OrganizacionSuscripcion findOrganizacionSuscripcionById(Long usuarioId);
}