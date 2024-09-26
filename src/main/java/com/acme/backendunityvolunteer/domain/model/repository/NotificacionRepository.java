package com.acme.backendunityvolunteer.domain.model.repository;

import com.acme.backendunityvolunteer.domain.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long>{
    List<Notificacion> findByUsuario_Id(Long usuarioId);
    //Notificacion findByActividad_Id(Long actividad);
}
