package com.acme.backendunityvolunteer.infraestructure.rest;

import com.acme.backendunityvolunteer.application.dto.user_management.PuntuacionService;
import com.acme.backendunityvolunteer.domain.model.Puntuacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/puntuaciones")
public class PuntuacionController {

    @Autowired
    private PuntuacionService puntuacionService;

    @PostMapping
    public ResponseEntity<Puntuacion> agregarPuntuacion(@RequestBody Puntuacion puntuacion) {
        Puntuacion nuevaPuntuacion = puntuacionService.addPuntuacion(
                puntuacion.getVoluntario().getId(),
                puntuacion.getActividad().getId(),
                puntuacion.getCalificacion(),
                puntuacion.getComentario()
        );
        return ResponseEntity.ok(nuevaPuntuacion);
    }
}