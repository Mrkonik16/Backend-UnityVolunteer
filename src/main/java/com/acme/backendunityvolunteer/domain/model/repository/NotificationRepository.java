package com.acme.backendunityvolunteer.domain.model.repository;

import com.acme.backendunityvolunteer.domain.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notificacion, Long> {
    Optional<Notificacion> findById(Long id);
    List<Notificacion> findByUsuarioId(Long usuarioId);
}
