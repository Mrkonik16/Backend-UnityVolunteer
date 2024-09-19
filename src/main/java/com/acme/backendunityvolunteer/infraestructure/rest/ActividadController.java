package com.acme.backendunityvolunteer.infraestructure.rest;

import com.acme.backendunityvolunteer.application.dto.ActividadDTO;
import com.acme.backendunityvolunteer.application.dto.user_management.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actividades")
public class ActividadController {
    @Autowired
    private ActividadService actividadService;

    //Devolver la lista de cada actividad por filtro
    @GetMapping("/{type}")
    public ResponseEntity<List<ActividadDTO>> obtenerActividadesPorTipo(@PathVariable String type) {
        List<ActividadDTO> actividades = actividadService.obtenerActividadesPorTipo(type);
        return ResponseEntity.ok(actividades);
    }
}
