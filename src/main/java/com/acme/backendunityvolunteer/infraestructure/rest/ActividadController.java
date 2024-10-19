package com.acme.backendunityvolunteer.infraestructure.rest;


import com.acme.backendunityvolunteer.application.dto.ActividadDTO;
import com.acme.backendunityvolunteer.application.dto.activity_management.ActividadService;
import com.acme.backendunityvolunteer.application.dto.activity_management.AssignationService;
import com.acme.backendunityvolunteer.application.dto.*;
import com.acme.backendunityvolunteer.application.dto.user_management.ActividadService2;
import com.acme.backendunityvolunteer.infraestructure.rest.dto.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/actividad")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;
    @Autowired
    private ActividadService2 actividadService2;

    @Autowired
    private AssignationService assignationService;


    // -------------------
    // Gestión de Actividades
    // -------------------

    //Devolver la lista de cada actividad por filtro
    @GetMapping("/{type}")
    public ResponseEntity<List<ActividadDTO>> obtenerActividadesPorTipo(@PathVariable String type) {
        List<ActividadDTO> actividades = actividadService2.obtenerActividadesPorTipo(type);
        return ResponseEntity.ok(actividades);
    }
  
    // Asignar una activdad a un voluntario
    @PostMapping("/asignar/{actividad_id}/voluntario/{voluntario_id}")
    public ResponseEntity<String> asignarActividadAVoluntario(@Valid @RequestBody VolunteerAssignationToActivityRequest request) {

        VolunteerAssignationToActivityDTO assignationDTO = new VolunteerAssignationToActivityDTO();
        assignationDTO.setActividadId(request.getActividadId());
        assignationDTO.setOrganizacionId(request.getOrganizacionId());
        assignationDTO.setVoluntarioId(request.getVoluntarioId());


        VolunteerAssignationToActivityDTO saveAssignation = assignationService.registrarVoluntarioAUnaActividad(assignationDTO);


        return ResponseEntity.ok("Actividad registrada con éxito");
    }

    // Registro de una nueva actividad
    @PostMapping("/registro")
    public ResponseEntity<String> registrarActividad(@Valid @RequestBody ActividadRequest request) {
        // Crear y guardar la actividad
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
        actividadDTO.setId(id);
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