package com.acme.backendunityvolunteer.infraestructure.rest;

import com.acme.backendunityvolunteer.application.dto.*;
import com.acme.backendunityvolunteer.application.dto.activity_management.ActividadService;
import com.acme.backendunityvolunteer.application.dto.user_management.OrganizacionSuscripcionService;
import com.acme.backendunityvolunteer.application.dto.user_management.PerfilOrganizacionService;
import com.acme.backendunityvolunteer.application.dto.user_management.PerfilVoluntarioService;
import com.acme.backendunityvolunteer.application.dto.user_management.UsuarioService;
import com.acme.backendunityvolunteer.domain.model.repository.PerfilOrganizacionRepository;
import com.acme.backendunityvolunteer.infraestructure.rest.dto.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/actividad")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;



    // -------------------
    // Gestión de Actividades
    // -------------------

    // Registro de una nueva actividad
    @PostMapping("/registro")
    public ResponseEntity<String> registrarActividad(@Valid @RequestBody ActividadRequest request) {
        // Crear y guardar el usuario primero
        ActividadDTO actividadDTO = new ActividadDTO();
        actividadDTO.setDuracion(request.getDuracion());
        actividadDTO.setNombre(request.getNombre());
        actividadDTO.setTipo(request.getTipo());


        ActividadDTO actividadGuardada = actividadService.registrarActividad(actividadDTO);


        return ResponseEntity.ok("Actividad registrada con éxito");
    }

    // Actualizar una actividad
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Void> actualizarActividad(@PathVariable Long id, @Valid @RequestBody ActividadRequest request) {
        ActividadDTO actividadDTO = new ActividadDTO();

        actividadDTO.setDuracion(request.getDuracion());
        actividadDTO.setNombre(request.getNombre());
        actividadDTO.setTipo(request.getTipo());
        actividadService.actualizarActividad(actividadDTO);
        return ResponseEntity.noContent().build();
    }

    // Eliminar una actividad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable Long id) {
        actividadService.eliminarActividad(id);
        return ResponseEntity.noContent().build();
    }


}