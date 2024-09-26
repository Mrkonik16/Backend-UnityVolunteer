package com.acme.backendunityvolunteer.infraestructure.rest;

import com.acme.backendunityvolunteer.application.dto.NotificacionDTO;
import com.acme.backendunityvolunteer.application.dto.notification_management.NotificacionService;
import com.acme.backendunityvolunteer.infraestructure.rest.dto.NotificacionRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    //-------------------
    // Gestión de Notificaciones
    //-------------------

    //Crear una nueva notificacion
    @PostMapping("/crear")
    public ResponseEntity<String> crearNotificacion(@Valid @RequestBody NotificacionRequest request){
        //Crear y guardar la notificacion
        if (request.getUsuarioId() == null) {
            return ResponseEntity.badRequest().body("El ID del usuario no puede ser nulo");
        }
        NotificacionDTO DTO = new NotificacionDTO();
        DTO.setUsuarioId(request.getUsuarioId());
        //DTO.setActividadId(request.getActividadId());
        DTO.setTitulo(request.getTitulo());
        DTO.setDescripcion(request.getDescripcion());
        DTO.setFecha_envio(request.getFecha_envio());
        DTO.setFecha_visto(request.getFecha_visto());

        NotificacionDTO notificacionGuardada = notificacionService.crearNotificacion(DTO);

        return ResponseEntity.ok("Notificacion creada con éxito");
    }

    //Obtener las notificaciones de un usuario segun su ID
    @GetMapping("/notificacion/{usuarioId}")
    public ResponseEntity<List<NotificacionDTO>> obtenerNotificacionesPorUsuarioId(@PathVariable Long usuarioId){
        List<NotificacionDTO> notificaciones = notificacionService.obtenerNotificacionesPorUsuarioId(usuarioId);
        return ResponseEntity.ok(notificaciones);
    }

    //Obtener las notificaciones relacionadas a una actividad
    //@GetMapping("/notificacion/{actividadId}")
    //public ResponseEntity<List<NotificacionDTO>> obtenerNotificacionesPorActividadId(@PathVariable Long actividadId){
        //List<NotificacionDTO> notificaciones = notificacionService.obtenerNotificacionesPorActividadId(actividadId);
        //return ResponseEntity.ok(notificaciones);
    //}

    //Eliminar una notificacion
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarNotificacion(@PathVariable Long id){
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}
