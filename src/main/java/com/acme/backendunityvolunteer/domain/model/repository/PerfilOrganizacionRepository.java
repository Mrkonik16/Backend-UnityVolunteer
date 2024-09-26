package com.acme.backendunityvolunteer.domain.model.repository;

import com.acme.backendunityvolunteer.domain.model.PerfilOrganizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilOrganizacionRepository extends JpaRepository<PerfilOrganizacion, Long> {
    PerfilOrganizacion findByUsuarioId(Long usuarioId);
}