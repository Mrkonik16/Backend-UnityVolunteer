package com.acme.backendunityvolunteer.domain.model.repository;

import com.acme.backendunityvolunteer.domain.model.SesionUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SesionUsuarioRepository extends JpaRepository<SesionUsuario, Long> {
    List<SesionUsuario> findByUsuarioId(Long usuarioId);
}