package com.acme.backendunityvolunteer.interfaces.persistence;

import com.acme.backendunityvolunteer.domain.model.SesionUsuario;
import com.acme.backendunityvolunteer.domain.model.repository.SesionUsuarioRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSesionUsuarioRepository extends JpaRepository<SesionUsuario, Long>, SesionUsuarioRepository {
}