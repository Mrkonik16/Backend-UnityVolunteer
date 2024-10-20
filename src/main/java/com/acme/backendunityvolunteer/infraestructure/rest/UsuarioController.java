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

import com.acme.backendunityvolunteer.interfaces.persistence.exception.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PerfilVoluntarioService perfilVoluntarioService;

    @Autowired
    private PerfilOrganizacionService perfilOrganizacionService;

    // Registro de un nuevo usuario (voluntario u organización)
    @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registrarUsuario(@Valid @RequestBody RegisterRequest request) {
        // Crear y guardar el usuario primero
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCorreo(request.getCorreo());
        usuarioDTO.setNombre(request.getNombre());
        usuarioDTO.setApellido(request.getApellido());
        usuarioDTO.setTelefono(request.getTelefono());
        usuarioDTO.setContrasenia(request.getContrasena());
        usuarioDTO.setTipoUsuario(request.getTipoUsuario());

        // Guardar el usuario en la base de datos
        UsuarioDTO usuarioGuardado = usuarioService.registrarUsuario(usuarioDTO);

        // Verificar si el usuario fue guardado correctamente
        if (usuarioGuardado == null || usuarioGuardado.getId() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al registrar el usuario"));
        }

        // Si el tipo de usuario es Voluntario, crear el perfil del voluntario
        if ("VOLUNTARIO".equalsIgnoreCase(request.getTipoUsuario())) {
            PerfilVoluntarioDTO perfilVoluntario = new PerfilVoluntarioDTO();
            perfilVoluntario.setUsuarioId(usuarioGuardado.getId());
            perfilVoluntario.setIntereses("Sin definir");
            perfilVoluntario.setExperiencia("Sin experiencia");
            perfilVoluntario.setDisponibilidad("No disponible");

            perfilVoluntarioService.crearPerfilVoluntario(perfilVoluntario);

        } else if ("ORGANIZACION".equalsIgnoreCase(request.getTipoUsuario())) {
            // Si el tipo de usuario es Organización, crear el perfil de organización
            PerfilOrganizacionDTO perfilOrganizacion = new PerfilOrganizacionDTO();
            perfilOrganizacion.setUsuarioId(usuarioGuardado.getId());
            perfilOrganizacion.setNombreOrganizacion(usuarioDTO.getNombre());
            perfilOrganizacion.setTipoOrganizacion("Sin definir");
            perfilOrganizacion.setSitioWeb("Sin sitio web");

            perfilOrganizacionService.crearPerfilOrganizacion(perfilOrganizacion);
        }

        // Crear un mapa con los datos que se devolverán como respuesta JSON
        Map<String, Object> response = new HashMap<>();
        response.put("usuarioId", usuarioGuardado.getId());
        response.put("mensaje", "Usuario y perfil registrados con éxito");

        // Devolver el usuarioId y el mensaje como JSON
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
    public ResponseEntity<Map<String, String>> actualizarPerfilVoluntario(@PathVariable Long usuarioId, @Valid @RequestBody PerfilVoluntarioDTO perfilVoluntario) {
        try {
            // Actualizar perfil del voluntario
            perfilVoluntario.setUsuarioId(usuarioId);
            perfilVoluntarioService.actualizarPerfil(perfilVoluntario);

            // Retornar mensaje de éxito con código 200 OK
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Perfil de voluntario actualizado con éxito");
            return ResponseEntity.ok(response);  // Devuelve 200 OK con un JSON
        } catch (Exception e) {
            // Manejar cualquier error que ocurra
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "Error al actualizar perfil de voluntario");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    // Obtener el perfil de una organización por su ID de usuario
    @GetMapping("/organizaciones/{usuarioId}")
    public ResponseEntity<PerfilOrganizacionDTO> obtenerPerfilOrganizacion(@PathVariable Long usuarioId) {
        PerfilOrganizacionDTO perfilOrganizacion = perfilOrganizacionService.obtenerPerfilPorUsuarioId(usuarioId);
        return ResponseEntity.ok(perfilOrganizacion);
    }

    // Actualizar perfil de organización
    @PutMapping("/organizaciones/{usuarioId}")
    public ResponseEntity<Map<String, String>> actualizarPerfilOrganizacion(@PathVariable Long usuarioId, @Valid @RequestBody PerfilOrganizacionDTO perfilOrganizacion) {
        try {
            // Actualizar perfil de la organización
            perfilOrganizacion.setUsuarioId(usuarioId);
            perfilOrganizacionService.actualizarPerfil(perfilOrganizacion);

            // Retornar mensaje de éxito con código 200 OK
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Perfil de organización actualizado con éxito");
            return ResponseEntity.ok(response);  // Devuelve 200 OK con un JSON

        } catch (Exception e) {
            // Manejar cualquier error que ocurra y retornar mensaje de error
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "Error al actualizar perfil de organización");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @GetMapping("/PerfilVoluntario/{usuarioId}")
    public ResponseEntity<Map<String, Object>> obtenerPerfilCompleto(@PathVariable Long usuarioId) {
        try {
            // Obtener los detalles del usuario por su ID
            UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorId(usuarioId);

            // Obtener el perfil del voluntario usando el usuarioId
            PerfilVoluntarioDTO perfilVoluntarioDTO = perfilVoluntarioService.obtenerPerfilPorUsuarioId(usuarioId);

            // Crear un mapa de respuesta para combinar ambos objetos
            Map<String, Object> response = new HashMap<>();
            response.put("correo", usuarioDTO.getCorreo());
            response.put("nombre", usuarioDTO.getNombre());
            response.put("apellido", usuarioDTO.getApellido());
            response.put("telefono", usuarioDTO.getTelefono());
            response.put("intereses", perfilVoluntarioDTO.getIntereses());
            response.put("experiencia", perfilVoluntarioDTO.getExperiencia());
            response.put("disponibilidad", perfilVoluntarioDTO.getDisponibilidad());

            // Devolver la respuesta con código 200 OK
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            // Si el usuario o perfil no es encontrado, devolver 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Usuario o perfil no encontrado"));
        } catch (Exception e) {
            // Cualquier otro error, devolver 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al obtener el perfil del usuario"));
        }
    }

    @GetMapping("/PerfilOrganizacion/{usuarioId}")
    public ResponseEntity<Map<String, Object>> obtenerPerfilOrganizacionCompleto(@PathVariable Long usuarioId) {
        try {
            // Obtener los detalles del usuario por su ID
            UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorId(usuarioId);

            // Obtener el perfil de la organización usando el usuarioId
            PerfilOrganizacionDTO perfilOrganizacionDTO = perfilOrganizacionService.obtenerPerfilPorUsuarioId(usuarioId);

            // Crear un mapa de respuesta para combinar ambos objetos
            Map<String, Object> response = new HashMap<>();
            response.put("correo", usuarioDTO.getCorreo());
            response.put("nombre", usuarioDTO.getNombre());
            response.put("apellido", usuarioDTO.getApellido());
            response.put("telefono", usuarioDTO.getTelefono());
            response.put("nombreOrganizacion", perfilOrganizacionDTO.getNombreOrganizacion());
            response.put("tipoOrganizacion", perfilOrganizacionDTO.getTipoOrganizacion());
            response.put("sitioWeb", perfilOrganizacionDTO.getSitioWeb());

            // Devolver la respuesta con código 200 OK
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            // Si el usuario o perfil no es encontrado, devolver 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Usuario o perfil no encontrado"));
        } catch (Exception e) {
            // Cualquier otro error, devolver 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al obtener el perfil del usuario"));
        }
    }



    // -------------------
    // Inicio de Sesión
    // -------------------

    // Iniciar sesión con correo y contraseña
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> iniciarSesion(@Valid @RequestBody LoginRequest request) {
        try {
            // Lógica de autenticación y generación de token
            String token = usuarioService.iniciarSesion(request.getCorreo(), request.getContrasena());

            // Obtener los detalles del usuario basado en el correo
            UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorCorreo(request.getCorreo());

            // Crear el objeto de respuesta con token, tipoUsuario, usuarioId y nombre
            AuthResponse response = new AuthResponse();
            response.setToken(token);
            response.setTipoUsuario(usuarioDTO.getTipoUsuario());
            response.setUsuarioId(usuarioDTO.getId());
            response.setNombre(usuarioDTO.getNombre());

            return ResponseEntity.ok(response);  // Devolver 200 OK con los datos del usuario
        } catch (IllegalArgumentException e) {
            // Manejar el error de contraseña incorrecta o usuario no encontrado
            if (e.getMessage().equals("Contraseña incorrecta")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // Devolver 401 Unauthorized
            } else if (e.getMessage().equals("Usuario no encontrado con ese correo")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Devolver 404 Not Found
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Cualquier otro error
            }
        }
    }

}