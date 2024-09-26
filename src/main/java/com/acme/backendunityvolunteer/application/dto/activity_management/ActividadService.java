package com.acme.backendunityvolunteer.application.dto.activity_management;

import com.acme.backendunityvolunteer.application.dto.ActividadDTO;
import com.acme.backendunityvolunteer.domain.model.Actividad;
import com.acme.backendunityvolunteer.domain.model.repository.ActividadRepository;
import com.acme.backendunityvolunteer.interfaces.persistence.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    public ActividadDTO registrarActividad(ActividadDTO actividadDTO) {
        Actividad nuevaActividad = new Actividad();
        nuevaActividad.setDuracion(actividadDTO.getDuracion());
        nuevaActividad.setNombre(actividadDTO.getNombre());
        nuevaActividad.setTipo(actividadDTO.getTipo());

        Actividad actividadGuardada = actividadRepository.save(nuevaActividad);
        return mapToDTO(actividadGuardada);
    }


    public ActividadDTO obtenerActividadPorId(Long id) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Actividad no encontrada"));

        return mapToDTO(actividad);
    }

    // Actualizar una actvidad
    public void actualizarActividad(ActividadDTO actividadDTO) {
        Actividad actividadExistente = actividadRepository.findById(actividadDTO.getId())
                .orElseThrow(() -> new NotFoundException("Actividad no encontrada"));

        actividadExistente.setDuracion(actividadDTO.getDuracion());
        actividadExistente.setNombre(actividadDTO.getNombre());
        actividadExistente.setTipo(actividadDTO.getTipo());

        actividadRepository.save(actividadExistente);
    }

    public void eliminarActividad(Long id) {
        actividadRepository.deleteById(id);
    }


    // Conversión de entidad a DTO
    private ActividadDTO mapToDTO(Actividad actividad) {
        ActividadDTO dto = new ActividadDTO();
        dto.setId(actividad.getId());
        dto.setNombre(actividad.getNombre());
        dto.setDuracion(actividad.getDuracion());
        dto.setTipo(actividad.getTipo());

        return dto;
    }
}