package com.acme.backendunityvolunteer.infraestructure.rest;

import com.acme.backendunityvolunteer.application.dto.*;
import com.acme.backendunityvolunteer.application.dto.payment_management.PagoService;
import com.acme.backendunityvolunteer.application.dto.user_management.OrganizacionSuscripcionService;
import com.acme.backendunityvolunteer.application.dto.user_management.PerfilOrganizacionService;
import com.acme.backendunityvolunteer.application.dto.user_management.UsuarioService;
import com.acme.backendunityvolunteer.domain.model.TipoSubscricion;
import com.acme.backendunityvolunteer.infraestructure.rest.dto.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/pago")
public class PagoController {

    @Autowired
    private PagoService pagoService;


    // -------------------
    // Gestión de Usuarios
    // -------------------

    // Registro de un nuevo pago
    @PostMapping("/")
    public ResponseEntity<String> realizarPago(@Valid @RequestBody PagoRequest request) {
        // Crear y guardar el usuario primero
        PagoDTO pagoDTO = new PagoDTO();
        pagoDTO.setOrganizationId(request.getOrganization_id());
        pagoDTO.setMonto(request.getMonto());
        pagoDTO.setTipoSubscricion(TipoSubscricion.valueOf(request.getTipoSubscripcion()));
        PagoDTO pagoGuardado = pagoService.registrarPago(pagoDTO);
        return ResponseEntity.ok("Pago registrado con éxito");
    }


    // -------------------
    // Gestión de Perfiles
    // -------------------

    // Obtener pago por su ID
    @GetMapping("/{pagoId}")
    public ResponseEntity<PagoDTO> obtenerPagoPorId(@PathVariable Long pagoId) {
        PagoDTO pago = pagoService.obtenerPagoPorId(pagoId);
        return ResponseEntity.ok(pago);
    }


    // Obtener pago(s) de una organización por su ID
    @GetMapping("/organizaciones/{organizacionId}")
    public ResponseEntity<PagoDTO> obtenerPerfilOrganizacion(@PathVariable Long organizacionId) {
        PagoDTO pagosDeLaOrganización = pagoService.obtenerPagoPorOrganizacionId(organizacionId);
        return ResponseEntity.ok(pagosDeLaOrganización);
    }



}