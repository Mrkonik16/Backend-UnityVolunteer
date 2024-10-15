package com.acme.backendunityvolunteer.application.dto.activity_management;

import com.acme.backendunityvolunteer.application.dto.ActividadDTO;
import com.acme.backendunityvolunteer.application.dto.VolunteerAssignationToActivityDTO;
import com.acme.backendunityvolunteer.domain.model.Actividad;
import com.acme.backendunityvolunteer.domain.model.Asignacion;
import com.acme.backendunityvolunteer.domain.model.repository.AsignacionRepository;
import com.acme.backendunityvolunteer.interfaces.persistence.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignationService {

    @Autowired
    private AsignacionRepository assignationRepository;

    public VolunteerAssignationToActivityDTO registrarVoluntarioAUnaActividad(VolunteerAssignationToActivityDTO assignationDTO) {
        Asignacion nuevaAsignacion = new Asignacion();
        nuevaAsignacion.setActividadId(assignationDTO.getActividadId());
        nuevaAsignacion.setOrganizacionId(assignationDTO.getOrganizacionId());
        nuevaAsignacion.setVoluntarioId(assignationDTO.getVoluntarioId());

        Asignacion assignationSaved = assignationRepository.save(nuevaAsignacion);
        return mapToDTO(assignationSaved);
    }


    public VolunteerAssignationToActivityDTO obtenerAsignacionPorId(Long asignacionId) {
        Asignacion asignacion = assignationRepository.findById(asignacionId)
                .orElseThrow(() -> new NotFoundException("No se encontró una asignacion con este ID"));

        return mapToDTO(asignacion);
    }

    // Actualizar una actvidad
    public void actualizarAsignacion(VolunteerAssignationToActivityDTO assignationDTO) {
        Asignacion asignacionExistente = assignationRepository.findById(assignationDTO.getId())
                .orElseThrow(() -> new NotFoundException("Asignacion no encontrada"));
        asignacionExistente.setVoluntarioId(assignationDTO.getVoluntarioId());
        assignationRepository.save(asignacionExistente);
    }

    public void eliminarAsignacion(Long asignacion_id) {
        assignationRepository.deleteById(asignacion_id);
    }


    // Conversión de entidad a DTO
    private VolunteerAssignationToActivityDTO mapToDTO(Asignacion assignation) {
        VolunteerAssignationToActivityDTO dto = new VolunteerAssignationToActivityDTO();
        dto.setId(assignation.getId());
        dto.setActividadId(assignation.getActividadId());
        dto.setOrganizacionId(assignation.getOrganizacionId());
        dto.setVoluntarioId(assignation.getVoluntarioId());

        return dto;
    }
}