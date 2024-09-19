package com.acme.backendunityvolunteer.domain.model.repository;

import com.acme.backendunityvolunteer.domain.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    List<Actividad> findByTipo(String tipo);
}
