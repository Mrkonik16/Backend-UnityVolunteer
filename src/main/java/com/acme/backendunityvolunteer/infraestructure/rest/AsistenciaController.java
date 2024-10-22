package com.acme.backendunityvolunteer.infraestructure.rest;

import com.acme.backendunityvolunteer.application.dto.AsistenciaDTO;
import com.acme.backendunityvolunteer.application.dto.activity_management.ActividadService;
import com.acme.backendunityvolunteer.application.dto.assist_management.AsistenciaService;
import com.acme.backendunityvolunteer.application.dto.user_management.UsuarioService;
import com.acme.backendunityvolunteer.infraestructure.rest.dto.AsistenciaRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencia")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ActividadService actividadService;

    // -------------------
    // Gestión de Asistencias
    // -------------------

    // Registro de una nueva asistencia
    @PostMapping("/Registro")
    public ResponseEntity<String> RegistrarAsistencia(@Valid @RequestBody AsistenciaRequest request) {
        // Crear y guardar la asistencia primero
        AsistenciaDTO asistenciaDTO = new AsistenciaDTO();
        asistenciaDTO.setUsuarioId(request.getUsuarioId());
        asistenciaDTO.setActividadId(request.getActividadId());
        asistenciaDTO.setEstado(request.getEstado());

        // Guardar la asistencia en la base de datos
        AsistenciaDTO asistenciaGuardada = asistenciaService.registrarAsistencia(asistenciaDTO);
        return ResponseEntity.ok("Asistencia registrada con éxito");
    }

    // Actualizar el estado de una asistencia
    @PutMapping("/Actualizar")
    public ResponseEntity<Void> ActualizarAsistencia(@PathVariable Long id, @Valid @RequestBody AsistenciaRequest request) {
        AsistenciaDTO asistenciaDTO = new AsistenciaDTO();
        asistenciaDTO.setUsuarioId(request.getUsuarioId());
        asistenciaDTO.setActividadId(request.getActividadId());
        asistenciaDTO.setEstado(request.getEstado());

        asistenciaService.actualizarAsistencia(asistenciaDTO);
        return ResponseEntity.noContent().build();
    }

    // Obtener las asistencias a eventos de un usuario
    @GetMapping("/Usuario/{id}")
    public ResponseEntity<List<AsistenciaDTO>> ObtenerAsistenciasPorUsuarioId(@PathVariable Long id) {
        List<AsistenciaDTO> asistencias = asistenciaService.obtenerAsistenciasPorUsuarioId(id);
        return ResponseEntity.ok(asistencias);
    }

    // Obtener las asistencias de los usuarios a un evento
    @GetMapping("Actividad/{id}")
    public ResponseEntity<List<AsistenciaDTO>> ObtenerAsistenciasPorActividadId(@PathVariable Long id) {
        List<AsistenciaDTO> asistencias = asistenciaService.obtenerAsistenciasPorActividadId(id);
        return ResponseEntity.ok(asistencias);
    }
}
