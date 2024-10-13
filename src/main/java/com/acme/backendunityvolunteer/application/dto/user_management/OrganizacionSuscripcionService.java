package com.acme.backendunityvolunteer.application.dto.user_management;

import com.acme.backendunityvolunteer.application.dto.OrganizacionSuscripcionDTO;
import com.acme.backendunityvolunteer.application.dto.PerfilOrganizacionDTO;
import com.acme.backendunityvolunteer.domain.model.OrganizacionSuscripcion;
import com.acme.backendunityvolunteer.domain.model.PerfilOrganizacion;
import com.acme.backendunityvolunteer.domain.model.TipoSubscricion;
import com.acme.backendunityvolunteer.domain.model.Usuario;
import com.acme.backendunityvolunteer.domain.model.repository.OrganizacionSuscripcionRepository;
import com.acme.backendunityvolunteer.domain.model.repository.PerfilOrganizacionRepository;
import com.acme.backendunityvolunteer.domain.model.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrganizacionSuscripcionService {

    @Autowired
    private OrganizacionSuscripcionRepository organizacionSuscripcionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PerfilOrganizacionRepository perfilOrganizacionRepository;

    // Obtener el perfil de organización por ID de usuario
    public OrganizacionSuscripcionDTO obtenerOrganizacionSuscripcionPorUsuarioId(Long usuarioId) {
        OrganizacionSuscripcion organizacionSuscripcion = organizacionSuscripcionRepository.findOrganizacionSuscripcionById(usuarioId);
        if (organizacionSuscripcion == null) {
            throw new RuntimeException("Suscripcion de la organización no encontrada para usuario con ID: " + usuarioId);
        }

        return mapToDTO(organizacionSuscripcion);
    }

    // Método para actualizar suscripcion de organización
    @Transactional
    public void actualizarSuscripcion(OrganizacionSuscripcionDTO perfilDTO) {
        OrganizacionSuscripcion perfil = organizacionSuscripcionRepository.findOrganizacionSuscripcionById(perfilDTO.getOrganizacionId());
        if (perfil == null) {
            throw new RuntimeException("Perfil de organización no encontrado para el ID: " + perfilDTO.getOrganizacionId());
        }

        // Actualizar los campos del perfil
        perfil.setSubscripcion(perfilDTO.getSubscripcion());
        perfil.setOrganizacionId(perfilDTO.getOrganizacionId());

        organizacionSuscripcionRepository.save(perfil);
    }

    @Transactional
    public void crearPerfilOrganizacion(PerfilOrganizacionDTO perfilDTO) {

        if (perfilDTO.getUsuarioId() == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }
        Usuario usuario = usuarioRepository.findById(perfilDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + perfilDTO.getUsuarioId()));

        PerfilOrganizacion perfil = new PerfilOrganizacion();
        // Asignamos el usuario al perfil
        perfil.setUsuario(usuario);

        perfil.setNombreOrganizacion(perfilDTO.getNombreOrganizacion() != null ? perfilDTO.getNombreOrganizacion() : "Organización sin nombre");
        perfil.setTipoOrganizacion(perfilDTO.getTipoOrganizacion() != null ? perfilDTO.getTipoOrganizacion() : "Sin definir");
        perfil.setSitioWeb(perfilDTO.getSitioWeb() != null ? perfilDTO.getSitioWeb() : "Sin sitio web");
        // Guardamos el perfil en la base de datos
        perfilOrganizacionRepository.save(perfil);
    }


    // Método auxiliar para convertir PerfilOrganizacion a PerfilOrganizacionDTO
    private OrganizacionSuscripcionDTO mapToDTO(OrganizacionSuscripcion perfil) {
        OrganizacionSuscripcionDTO dto = new OrganizacionSuscripcionDTO();
        dto.setOrganizacionId(perfil.getOrganizacionId());
        dto.setSubscripcion(perfil.getSubscripcion());

        return dto;
    }
}