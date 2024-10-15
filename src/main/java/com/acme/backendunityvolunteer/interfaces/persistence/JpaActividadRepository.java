package com.acme.backendunityvolunteer.interfaces.persistence;

import com.acme.backendunityvolunteer.domain.model.Actividad;
import com.acme.backendunityvolunteer.domain.model.repository.ActividadRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaActividadRepository extends JpaRepository<Actividad, Long>, ActividadRepository {
}
