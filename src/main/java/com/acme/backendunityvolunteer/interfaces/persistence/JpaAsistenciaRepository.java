package com.acme.backendunityvolunteer.interfaces.persistence;

import com.acme.backendunityvolunteer.domain.model.Asistencia;
import com.acme.backendunityvolunteer.domain.model.repository.AsistenciaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAsistenciaRepository extends JpaRepository<Asistencia, Long>, AsistenciaRepository {
}
