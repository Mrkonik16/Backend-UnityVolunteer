package com.acme.backendunityvolunteer.application.dto.user_management;

import com.acme.backendunityvolunteer.application.dto.PerfilVoluntarioDTO;
import com.acme.backendunityvolunteer.domain.model.PerfilVoluntario;
import com.acme.backendunityvolunteer.domain.model.Usuario;
import com.acme.backendunityvolunteer.domain.model.repository.PerfilVoluntarioRepository;
import com.acme.backendunityvolunteer.domain.model.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PerfilVoluntarioService {

    @Autowired
    private PerfilVoluntarioRepository perfilVoluntarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public PerfilVoluntarioDTO obtenerPerfilPorUsuarioId(Long usuarioId) {
        PerfilVoluntario perfil = perfilVoluntarioRepository.findByUsuarioId(usuarioId);
        if (perfil == null) {
            throw new RuntimeException("Perfil de voluntario no encontrado para el usuario con ID: " + usuarioId);
        }
        return mapToDTO(perfil);
    }

    // Método para actualizar un perfil de voluntario
    @Transactional
    public void actualizarPerfil(PerfilVoluntarioDTO perfilDTO) {
        PerfilVoluntario perfil = perfilVoluntarioRepository.findByUsuarioId(perfilDTO.getUsuarioId());
        if (perfil == null) {
            throw new RuntimeException("Perfil de voluntario no encontrado para el usuario con ID: " + perfilDTO.getUsuarioId());
        }

        perfil.setIntereses(perfilDTO.getIntereses());
        perfil.setExperiencia(perfilDTO.getExperiencia());
        perfil.setDisponibilidad(perfilDTO.getDisponibilidad());

        perfilVoluntarioRepository.save(perfil);
    }

    @Transactional
    public void crearPerfilVoluntario(PerfilVoluntarioDTO perfilDTO) {
        if (perfilDTO.getUsuarioId() == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }

        Usuario usuario = usuarioRepository.findById(perfilDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + perfilDTO.getUsuarioId()));

        PerfilVoluntario perfil = new PerfilVoluntario();
        // Asignamos el usuario al perfil
        perfil.setUsuario(usuario);
        perfil.setIntereses(perfilDTO.getIntereses());
        perfil.setExperiencia(perfilDTO.getExperiencia());
        perfil.setDisponibilidad(perfilDTO.getDisponibilidad());
        perfil.setRating(perfilDTO.getRating());

        perfilVoluntarioRepository.save(perfil);
    }

    // Método auxiliar para convertir PerfilVoluntario a PerfilVoluntarioDTO
    private PerfilVoluntarioDTO mapToDTO(PerfilVoluntario perfil) {
        PerfilVoluntarioDTO dto = new PerfilVoluntarioDTO();
        dto.setId(perfil.getId());
        dto.setUsuarioId(perfil.getUsuario().getId());
        dto.setIntereses(perfil.getIntereses());
        dto.setExperiencia(perfil.getExperiencia());
        dto.setDisponibilidad(perfil.getDisponibilidad());
        dto.setRating(perfil.getRating());
        return dto;
    }
}