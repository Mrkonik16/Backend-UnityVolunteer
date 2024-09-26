package com.acme.backendunityvolunteer.application.dto.notification_management;

import com.acme.backendunityvolunteer.application.dto.NotificacionDTO;
import com.acme.backendunityvolunteer.domain.model.Notificacion;
import com.acme.backendunityvolunteer.domain.model.Usuario;
import com.acme.backendunityvolunteer.domain.model.repository.NotificacionRepository;
import com.acme.backendunityvolunteer.domain.model.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //@Autowired
    //private ActividadRepository actividadRepository;

    //Obtener las notificaciones del usuario segun su ID
    public List<NotificacionDTO> obtenerNotificacionesPorUsuarioId(Long usuarioId) {
        List<Notificacion> notificaciones = notificacionRepository.findByUsuario_Id(usuarioId);
        if (notificaciones == null) {
            throw new RuntimeException("Notificacion no encontrada para el usuario con ID: " + usuarioId);
        }

        return notificaciones.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void eliminarNotificacion(Long id) {
        notificacionRepository.deleteById(id);
    }

    @Transactional
    public NotificacionDTO crearNotificacion(NotificacionDTO notificacionDTO){
        if (notificacionDTO.getUsuarioId() == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }
        Usuario usuario = usuarioRepository.findById(notificacionDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + notificacionDTO.getUsuarioId()));

        Notificacion notificacion = new Notificacion();
        //Asignamos el usuario a la notificacion
        notificacion.setUsuario(usuario);
        notificacion.setTitulo(notificacionDTO.getTitulo());
        notificacion.setDescripcion(notificacionDTO.getDescripcion());
        notificacion.setFecha_envio(notificacionDTO.getFecha_envio());
        notificacion.setFecha_visto(notificacionDTO.getFecha_visto());

        notificacionRepository.save(notificacion);
        return notificacionDTO;
    }

    private NotificacionDTO mapToDTO(Notificacion notificacion){
        NotificacionDTO DTO = new NotificacionDTO();
        DTO.setId(notificacion.getId());
        DTO.setUsuarioId(notificacion.getUsuario().getId());
        //DTO.setActividadId(notificacion.getActividad().getId());
        DTO.setTitulo(notificacion.getTitulo());
        DTO.setDescripcion(notificacion.getDescripcion());
        DTO.setFecha_envio(notificacion.getFecha_envio());
        DTO.setFecha_visto(notificacion.getFecha_visto());
        return DTO;
    }
}
