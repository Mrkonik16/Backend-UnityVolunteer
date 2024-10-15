package com.acme.backendunityvolunteer.domain.model.repository;

import com.acme.backendunityvolunteer.domain.model.Actividad;
import com.acme.backendunityvolunteer.domain.model.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    Optional<Asignacion> findById(Long id);

}


