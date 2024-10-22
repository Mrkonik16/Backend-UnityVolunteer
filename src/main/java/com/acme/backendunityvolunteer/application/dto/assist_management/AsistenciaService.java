package com.acme.backendunityvolunteer.application.dto.assist_management;

import com.acme.backendunityvolunteer.application.dto.AsistenciaDTO;
import com.acme.backendunityvolunteer.application.dto.UsuarioDTO;
import com.acme.backendunityvolunteer.domain.model.Asistencia;
import com.acme.backendunityvolunteer.domain.model.TipoConfirmacion;
import com.acme.backendunityvolunteer.domain.model.repository.ActividadRepository;
import com.acme.backendunityvolunteer.domain.model.repository.AsistenciaRepository;
import com.acme.backendunityvolunteer.domain.model.repository.UsuarioRepository;
import com.acme.backendunityvolunteer.interfaces.persistence.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    public AsistenciaDTO registrarAsistencia(AsistenciaDTO asistenciaDTO) {
        if (asistenciaDTO.getUsuarioId() == null) {
            throw new IllegalArgumentException("Usuario ID must not be null");
        }
        if (asistenciaDTO.getActividadId() == null) {
            throw new IllegalArgumentException("Actividad ID must not be null");
        }

        Asistencia nuevaAsistencia = new Asistencia();
        nuevaAsistencia.setUsuario(usuarioRepository.findById(asistenciaDTO.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado")));
        nuevaAsistencia.setActividad(actividadRepository.findById(asistenciaDTO.getActividadId())
                .orElseThrow(() -> new NotFoundException("Actividad no encontrada")));

        try{
            nuevaAsistencia.setTipoConfirmacion(TipoConfirmacion.valueOf(asistenciaDTO.getEstado().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de confirmación inválida.");
        }

        Asistencia asistenciaGuardada = asistenciaRepository.save(nuevaAsistencia);
        return mapToDTO(asistenciaGuardada);
    }

    public List<AsistenciaDTO> obtenerAsistenciasPorUsuarioId(Long usuarioId){
        List<Asistencia> asistencias = asistenciaRepository.findByUsuarioId(usuarioId);
        if(asistencias.isEmpty()) {
            throw new NotFoundException("No se encontraron asistencias para el usuario con ID: " + usuarioId);
        }
        return asistencias.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<AsistenciaDTO> obtenerAsistenciasPorActividadId(Long actividadId){
        List<Asistencia> asistencias = asistenciaRepository.findByActividadId(actividadId);
        if(asistencias.isEmpty()) {
            throw new NotFoundException("No se encontraron asistencias para la actividad con ID: " + actividadId);
        }
        return asistencias.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void actualizarAsistencia(AsistenciaDTO asistenciaDTO) {
        Asistencia asistenciaExistente = asistenciaRepository.findById(asistenciaDTO.getId())
                .orElseThrow(() -> new NotFoundException("Asistencia no encontrada"));

        asistenciaExistente.setTipoConfirmacion(TipoConfirmacion.valueOf(asistenciaDTO.getEstado().toUpperCase()));

        asistenciaRepository.save(asistenciaExistente);
    }

    private AsistenciaDTO mapToDTO(Asistencia asistencia) {
        AsistenciaDTO asistenciaDTO = new AsistenciaDTO();
        asistenciaDTO.setId(asistencia.getId());
        asistenciaDTO.setUsuarioId(asistencia.getUsuario().getId());
        asistenciaDTO.setActividadId(asistencia.getActividad().getId());
        asistenciaDTO.setEstado(asistencia.getTipoConfirmacion().name());
        return asistenciaDTO;
    }
}
