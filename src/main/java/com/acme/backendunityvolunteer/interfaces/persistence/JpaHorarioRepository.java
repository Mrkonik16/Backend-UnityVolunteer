package com.acme.backendunityvolunteer.interfaces.persistence;

import com.acme.backendunityvolunteer.domain.model.Horario;
import com.acme.backendunityvolunteer.domain.model.repository.HorarioRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaHorarioRepository extends JpaRepository<Horario, Long>, HorarioRepository {
}
