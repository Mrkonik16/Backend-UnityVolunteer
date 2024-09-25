package com.acme.backendunityvolunteer.infraestructure.rest;

import com.acme.backendunityvolunteer.application.dto.OrganizacionSuscripcionDTO;
import com.acme.backendunityvolunteer.application.dto.PerfilOrganizacionDTO;
import com.acme.backendunityvolunteer.application.dto.PerfilVoluntarioDTO;
import com.acme.backendunityvolunteer.application.dto.UsuarioDTO;
import com.acme.backendunityvolunteer.application.dto.user_management.*;
import com.acme.backendunityvolunteer.domain.model.repository.PerfilOrganizacionRepository;
import com.acme.backendunityvolunteer.infraestructure.rest.dto.AuthResponse;
import com.acme.backendunityvolunteer.infraestructure.rest.dto.LoginRequest;
import com.acme.backendunityvolunteer.infraestructure.rest.dto.RegisterRequest;
import com.acme.backendunityvolunteer.infraestructure.rest.dto.UsuarioUpdateRequest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PerfilVoluntarioService perfilVoluntarioService;

    @Autowired
    private PerfilOrganizacionService perfilOrganizacionService;
    @Autowired
    private OrganizacionSuscripcionService organizacionSuscripcionService;

    @Autowired
    private PerfilOrganizacionRepository perfilOrganizacionRepository;

    // -------------------
    // Gestión de Usuarios
    // -------------------

    // Registro de un nuevo usuario (voluntario u organización)
    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@Valid @RequestBody RegisterRequest request) {
        // Crear y guardar el usuario primero
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCorreo(request.getCorreo());
        usuarioDTO.setNombre(request.getNombre());
        usuarioDTO.setApellido(request.getApellido());
        usuarioDTO.setTelefono(request.getTelefono());
        usuarioDTO.setContrasenia(request.getContrasena());
        usuarioDTO.setTipoUsuario(request.getTipoUsuario());


        UsuarioDTO usuarioGuardado = usuarioService.registrarUsuario(usuarioDTO);

        // Si el tipo de usuario es Voluntario u Organización, registramos los perfiles
        if ("VOLUNTARIO".equalsIgnoreCase(request.getTipoUsuario())) {
            PerfilVoluntarioDTO perfilVoluntario = new PerfilVoluntarioDTO();
            perfilVoluntario.setUsuarioId(usuarioGuardado.getId());
            perfilVoluntarioService.crearPerfilVoluntario(perfilVoluntario);
        } else if ("ORGANIZACION".equalsIgnoreCase(request.getTipoUsuario())) {

            PerfilOrganizacionDTO perfilOrganizacion = new PerfilOrganizacionDTO();
            perfilOrganizacion.setUsuarioId(usuarioGuardado.getId());
            perfilOrganizacion.setNombreOrganizacion(usuarioDTO.getNombre());
            perfilOrganizacion.setTipoOrganizacion("Sin definir");
            perfilOrganizacion.setSitioWeb("Sin sitio web");
            perfilOrganizacionService.crearPerfilOrganizacion(perfilOrganizacion);
        }

        return ResponseEntity.ok("Usuario registrado con éxito");
    }

    // Actualizar un usuario
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Void> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateRequest request) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(id);
        usuarioDTO.setCorreo(request.getCorreo());
        usuarioDTO.setNombre(request.getNombre());
        usuarioDTO.setApellido(request.getApellido());
        usuarioDTO.setTelefono(request.getTelefono());
        usuarioDTO.setTipoUsuario(request.getTipoUsuario());

        usuarioService.actualizarUsuario(usuarioDTO);
        return ResponseEntity.noContent().build();
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // -------------------
    // Gestión de Perfiles
    // -------------------

    // Obtener el perfil de un voluntario por su ID de usuario
    @GetMapping("/voluntarios/{usuarioId}")
    public ResponseEntity<PerfilVoluntarioDTO> obtenerPerfilVoluntario(@PathVariable Long usuarioId) {
        PerfilVoluntarioDTO perfilVoluntario = perfilVoluntarioService.obtenerPerfilPorUsuarioId(usuarioId);
        return ResponseEntity.ok(perfilVoluntario);
    }

    // Actualizar perfil de voluntario
    @PutMapping("/voluntarios/{usuarioId}")
    public ResponseEntity<Void> actualizarPerfilVoluntario(@PathVariable Long usuarioId, @Valid @RequestBody PerfilVoluntarioDTO perfilVoluntario) {
        perfilVoluntario.setUsuarioId(usuarioId);
        perfilVoluntarioService.actualizarPerfil(perfilVoluntario);
        return ResponseEntity.noContent().build();
    }

    // Obtener el perfil de una organización por su ID de usuario
    @GetMapping("/organizaciones/{usuarioId}")
    public ResponseEntity<PerfilOrganizacionDTO> obtenerPerfilOrganizacion(@PathVariable Long usuarioId) {
        PerfilOrganizacionDTO perfilOrganizacion = perfilOrganizacionService.obtenerPerfilPorUsuarioId(usuarioId);
        return ResponseEntity.ok(perfilOrganizacion);
    }

    // Actualizar perfil de organización
    @PutMapping("/organizaciones/{usuarioId}")
    public ResponseEntity<Void> actualizarPerfilOrganizacion(@PathVariable Long usuarioId, @Valid @RequestBody PerfilOrganizacionDTO perfilOrganizacion) {
        perfilOrganizacion.setUsuarioId(usuarioId);
        perfilOrganizacionService.actualizarPerfil(perfilOrganizacion);
        return ResponseEntity.noContent().build();
    }

    // Actualizar suscripcion de una organización
    @PutMapping("/organizaciones/{organizacionId}")
    public ResponseEntity<Void> actualizarSuscripcionOrganizacion(@PathVariable Long organizacionId, @Valid @RequestBody OrganizacionSuscripcionDTO organizacionSuscripcion) {
        organizacionSuscripcionService.actualizarSuscripcion(organizacionSuscripcion);
        return ResponseEntity.noContent().build();
    }


    // -------------------
    // Inicio de Sesión
    // -------------------

    // Iniciar sesión con correo y contraseña
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> iniciarSesion(@Valid @RequestBody LoginRequest request) {
        String token = usuarioService.iniciarSesion(request.getCorreo(), request.getContrasena());
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorCorreo(request.getCorreo());

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setTipoUsuario(usuarioDTO.getTipoUsuario());

        return ResponseEntity.ok(response);
    }
}