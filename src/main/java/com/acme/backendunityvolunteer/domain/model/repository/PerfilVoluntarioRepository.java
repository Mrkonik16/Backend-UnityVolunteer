package com.acme.backendunityvolunteer.domain.model.repository;

import com.acme.backendunityvolunteer.domain.model.PerfilVoluntario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilVoluntarioRepository extends JpaRepository<PerfilVoluntario, Long> {
    PerfilVoluntario findByUsuarioId(Long usuarioId);
}