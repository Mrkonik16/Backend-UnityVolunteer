package com.acme.backendunityvolunteer.interfaces.persistence;

import com.acme.backendunityvolunteer.domain.model.Notificacion;
import com.acme.backendunityvolunteer.domain.model.repository.NotificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotificationRepository extends JpaRepository<Notificacion, Long>, NotificationRepository {
}
