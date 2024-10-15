package com.acme.backendunityvolunteer.application.dto.schedule_management;

import com.acme.backendunityvolunteer.application.dto.HorarioDTO;
import com.acme.backendunityvolunteer.domain.model.Horario;
import com.acme.backendunityvolunteer.domain.model.repository.HorarioRepository;
import com.acme.backendunityvolunteer.domain.model.repository.UsuarioRepository;
import com.acme.backendunityvolunteer.interfaces.persistence.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public HorarioDTO obtenerHorarioPorId(Long id) {
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Horario no encontrado"));

        return mapToDTO(horario);
    }

    public HorarioDTO obtenerHorarioPorUsuarioId(Long usuarioId){
        Horario horario = horarioRepository.findByUsuarioId(usuarioId);
        if (horario == null) {
            throw new NotFoundException("Horario no encontrado para el usuario con ID: " + usuarioId);
        }
        return mapToDTO(horario);
    }

    //Metodo para crear un horario
    public HorarioDTO registrarHorario(HorarioDTO horarioDTO){
        Horario nuevoHorario = new Horario();
        nuevoHorario.setNombre(horarioDTO.getNombre());
        nuevoHorario.setNum_actividades(horarioDTO.getNum_Actividades());
        nuevoHorario.setNum_Horas(horarioDTO.getNum_Horas());
        nuevoHorario.setUsuario(usuarioRepository.findById(horarioDTO.getUserid())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado")));

        Horario horarioGuardado = horarioRepository.save(nuevoHorario);
        return mapToDTO(horarioGuardado);
    }

    public void actualizarHorario(HorarioDTO horarioDTO){
        Horario horarioExistente = horarioRepository.findById(horarioDTO.getId())
                .orElseThrow(() -> new NotFoundException("Horario no encontrado"));

        horarioExistente.setId(horarioDTO.getId());
        horarioExistente.setNombre(horarioDTO.getNombre());
        horarioExistente.setNum_actividades(horarioDTO.getNum_Actividades());
        horarioExistente.setNum_Horas(horarioDTO.getNum_Horas());

        horarioRepository.save(horarioExistente);
    }
    
    private HorarioDTO mapToDTO(Horario horario){
        HorarioDTO dto = new HorarioDTO();
        dto.setId(horario.getId());
        dto.setUserid(horario.getUsuario().getId());
        dto.setNombre(horario.getNombre());
        dto.setNum_Actividades(horario.getNum_actividades());
        dto.setNum_Horas(horario.getNum_Horas());

        return dto;
    }
}
