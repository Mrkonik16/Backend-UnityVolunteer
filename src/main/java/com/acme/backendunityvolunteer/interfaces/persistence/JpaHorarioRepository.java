package com.acme.backendunityvolunteer.interfaces.persistence;

import com.acme.backendunityvolunteer.domain.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaHorarioRepository extends JpaRepository<Horario, Long> {
}
