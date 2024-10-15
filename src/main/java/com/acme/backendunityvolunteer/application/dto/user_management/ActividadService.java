package com.acme.backendunityvolunteer.application.dto.user_management;

import com.acme.backendunityvolunteer.application.dto.ActividadDTO;
import com.acme.backendunityvolunteer.domain.model.Actividad;
import com.acme.backendunityvolunteer.domain.model.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActividadService {
    @Autowired
    private ActividadRepository actividadRepository;

    public List<ActividadDTO> obtenerActividadesPorTipo(String tipo) {
        List<Actividad> actividades = actividadRepository.findByTipo(tipo);
        return actividades.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ActividadDTO mapToDTO(Actividad actividad) {
        ActividadDTO dto = new ActividadDTO();
        dto.setId(actividad.getId());
        dto.setNombre(actividad.getNombre());
        dto.setDescripcion(actividad.getDescripcion());
        dto.setTipo(actividad.getTipo());
        dto.setDuracion(actividad.getDuracion());
        return dto;
    }
}
