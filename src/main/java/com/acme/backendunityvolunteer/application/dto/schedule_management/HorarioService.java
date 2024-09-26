package com.acme.backendunityvolunteer.application.dto.schedule_management;

import com.acme.backendunityvolunteer.application.dto.HorarioDTO;
import com.acme.backendunityvolunteer.domain.model.Horario;
import com.acme.backendunityvolunteer.domain.model.Usuario;
import com.acme.backendunityvolunteer.domain.model.repository.HorarioRepository;
import com.acme.backendunityvolunteer.domain.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Obtener el horario del usuario segun su ID
    public HorarioDTO obtenerHorarioPorUsuarioId(Long usuarioId) {
        Horario horario = horarioRepository.findByUsuarioid_Id(usuarioId);
        if (horario == null) {
            throw new RuntimeException("Horario no encontrado para el usuario con ID: " + usuarioId);
        }

        return mapToDTO(horario);
    }

    public void eliminarHorario(Long id) {
        horarioRepository.deleteById(id);
    }

    @Transactional
    public void actualizarHorario(HorarioDTO horarioDTO) {
        Horario horario = horarioRepository.findByUsuarioid_Id(horarioDTO.getUsuarioId());
        if (horario == null) {
            throw new RuntimeException("Horario no encontrado para el usuario con ID: " + horarioDTO.getUsuarioId());
        }

        //Actualizar los campos del horario
        horario.setNombre(horarioDTO.getNombre());

        horarioRepository.save(horario);
    }

    @Transactional
    public HorarioDTO crearHorario(HorarioDTO horarioDTO) {

        if (horarioDTO.getUsuarioId() == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }
        Usuario usuario = usuarioRepository.findById(horarioDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + horarioDTO.getUsuarioId()));

        Horario horario = new Horario();
        //Asignamos el usuario al horario
        horario.setUsuarioid(usuario);
        horario.setNombre(horarioDTO.getNombre());

        horarioRepository.save(horario);
        return horarioDTO;
    }

    private HorarioDTO mapToDTO(Horario horario) {
        HorarioDTO horarioDTO = new HorarioDTO();
        horarioDTO.setUsuarioId(horario.getUsuarioid().getId());
        horarioDTO.setNombre(horario.getNombre());

        return horarioDTO;
    }
}
