package com.acme.backendunityvolunteer.domain.model.repository;

import com.acme.backendunityvolunteer.domain.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    Optional<Asistencia> findById(Long id);
    List<Asistencia> findByUsuarioId(Long usuarioId);
    List<Asistencia> findByActividadId(Long actividadId);
}
