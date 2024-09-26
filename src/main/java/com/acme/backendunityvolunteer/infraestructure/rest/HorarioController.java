package com.acme.backendunityvolunteer.infraestructure.rest;

import com.acme.backendunityvolunteer.application.dto.HorarioDTO;
import com.acme.backendunityvolunteer.application.dto.schedule_management.HorarioService;
import com.acme.backendunityvolunteer.infraestructure.rest.dto.HorarioRequest;
import com.acme.backendunityvolunteer.infraestructure.rest.dto.HorarioUpdateRequest;
import com.acme.backendunityvolunteer.infraestructure.rest.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    // -------------------
    // Gestión de Horarios
    // -------------------

    // Crear un nuevo horario
    @PostMapping("/crear")
    public ResponseEntity<String> crearHorario(@Valid @RequestBody HorarioRequest request) {
        // Crear y guardar el horario
        if (request.getUsuarioId() == null) {
            return ResponseEntity.badRequest().body("El ID del usuario no puede ser nulo");
        }
        HorarioDTO horarioDTO = new HorarioDTO();
        horarioDTO.setUsuarioId(request.getUsuarioId());
        horarioDTO.setNombre(request.getNombreHorario());
        horarioDTO.setNum_Actividades(request.getNum_Actividades());
        horarioDTO.setNum_Horas(request.getNum_Horas());

        HorarioDTO horarioGuardado = horarioService.crearHorario(horarioDTO);

        return ResponseEntity.ok("Horario creado con éxito");
    }

    //Actualizar un horario
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarHorario(@PathVariable Long id, @Valid @RequestBody HorarioUpdateRequest request){
        HorarioDTO horarioDTO = new HorarioDTO();
        horarioDTO.setId(id);
        horarioDTO.setUsuarioId(request.getUsuarioId());
        horarioDTO.setNombre(request.getNombreHorario());
        horarioDTO.setNum_Actividades(request.getNum_Actividades());
        horarioDTO.setNum_Horas(request.getNum_Horas());

        horarioService.actualizarHorario(horarioDTO);
        return ResponseEntity.noContent().build();
    }

    //Obtener el horario de un usuario segun su ID
    @GetMapping("/horario/{usuarioId}")
    public ResponseEntity<HorarioDTO> obtenerHorarioPorUsuarioId(@PathVariable Long usuarioId){
        HorarioDTO horarioDTO = horarioService.obtenerHorarioPorUsuarioId(usuarioId);
        return ResponseEntity.ok(horarioDTO);
    }

    //Eliminar un horario
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarHorario(@PathVariable Long id){
        horarioService.eliminarHorario(id);
        return ResponseEntity.noContent().build();
    }
}
