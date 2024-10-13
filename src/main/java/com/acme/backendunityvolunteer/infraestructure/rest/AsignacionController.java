package com.acme.backendunityvolunteer.infraestructure.rest;

import com.acme.backendunityvolunteer.application.dto.ActividadDTO;
import com.acme.backendunityvolunteer.application.dto.VolunteerAssignationToActivityDTO;
import com.acme.backendunityvolunteer.application.dto.activity_management.ActividadService;
import com.acme.backendunityvolunteer.application.dto.activity_management.AssignationService;
import com.acme.backendunityvolunteer.infraestructure.rest.dto.ActividadRequest;
import com.acme.backendunityvolunteer.infraestructure.rest.dto.VolunteerAssignationToActivityRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/asignacion")
public class AsignacionController {

    @Autowired
    private AssignationService assignationService;


    // -------------------
    // Gestión de Asignaciones
    // -------------------


    // Asignar una activdad a un voluntario
    @PostMapping("/{actividad_id}/voluntario/{voluntario_id}")
    public ResponseEntity<String> asignarActividadAVoluntario(@Valid @RequestBody VolunteerAssignationToActivityRequest request) {

        VolunteerAssignationToActivityDTO assignationDTO = new VolunteerAssignationToActivityDTO();
        assignationDTO.setActividadId(request.getActividadId());
        assignationDTO.setOrganizacionId(request.getOrganizacionId());
        assignationDTO.setVoluntarioId(request.getVoluntarioId());


        VolunteerAssignationToActivityDTO saveAssignation = assignationService.registrarVoluntarioAUnaActividad(assignationDTO);
        return ResponseEntity.ok("Actividad registrada con éxito");
    }

    // Actualizar el id del voluntario de una actividad (remplazar el id de un voluntario por otro, en una actividad)
    @PutMapping("{actividad_id}/actualizar/{voluntario_id}")
    public ResponseEntity<Void> actualizarAsignacion(@PathVariable Long actividad_id, long voluntario_id) {
        VolunteerAssignationToActivityDTO assignationDTO = new VolunteerAssignationToActivityDTO();
        assignationDTO.setId(actividad_id);
        assignationDTO.setVoluntarioId(voluntario_id);

        assignationService.actualizarAsignacion(assignationDTO);
        return ResponseEntity.noContent().build();
    }

    // Eliminar una asignacion
    @DeleteMapping("/{asignacion_id}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable Long asignacion_id) {
        assignationService.eliminarAsignacion(asignacion_id);
        return ResponseEntity.noContent().build();
    }

}