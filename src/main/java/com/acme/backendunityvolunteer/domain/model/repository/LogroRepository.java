package com.acme.backendunityvolunteer.domain.model.repository;

import com.acme.backendunityvolunteer.domain.model.Logros;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogroRepository extends JpaRepository<Logros, Long> {
}