package com.acme.backendunityvolunteer.infraestructure.rest;

import com.acme.backendunityvolunteer.application.dto.HorarioDTO;
import com.acme.backendunityvolunteer.application.dto.schedule_management.HorarioService;
import com.acme.backendunityvolunteer.application.dto.user_management.UsuarioService;
import com.acme.backendunityvolunteer.infraestructure.rest.dto.HorarioRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/horario")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private UsuarioService usuarioService;

    // -------------------
    // Gestión de Horarios
    // -------------------

    // Registro de un nuevo horario
    @PostMapping("/Registro")
    public ResponseEntity<String> RegistrarHorario(@Valid @RequestBody HorarioRequest request) {
        // Crear y guardar el horario primero
        HorarioDTO horarioDTO = new HorarioDTO();
        horarioDTO.setUserid(request.getUserid());
        horarioDTO.setNombre(request.getNombre());
        horarioDTO.setNum_Actividades(request.getNum_Actividades());
        horarioDTO.setNum_Horas(request.getNum_Horas());

        HorarioDTO horarioGuardado = horarioService.registrarHorario(horarioDTO);
        return ResponseEntity.ok("Horario registrado con éxito");
    }

    //Actualizar un horario
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Void> ActualizarHorario(@PathVariable Long id, @Valid @RequestBody HorarioRequest request) {
        HorarioDTO horarioDTO = new HorarioDTO();

        horarioDTO.setUserid(id);
        horarioDTO.setNombre(request.getNombre());
        horarioDTO.setNum_Actividades(request.getNum_Actividades());
        horarioDTO.setNum_Horas(request.getNum_Horas());

        horarioService.actualizarHorario(horarioDTO);
        return ResponseEntity.noContent().build();
    }

    //Obtener horario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<HorarioDTO> obtenerHorarioPorId(@PathVariable Long id) {
        HorarioDTO horario = horarioService.obtenerHorarioPorId(id);
        return ResponseEntity.ok(horario);
    }

    //Obtener horario de un usuario por su ID
    @GetMapping("/usuario/{id}")
    public ResponseEntity<HorarioDTO> obtenerHorarioPorUsuarioId(@PathVariable Long id) {
        HorarioDTO horario = horarioService.obtenerHorarioPorUsuarioId(id);
        return ResponseEntity.ok(horario);
    }
}
