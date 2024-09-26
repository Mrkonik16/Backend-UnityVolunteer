package com.acme.backendunityvolunteer.application.dto.paymanet_management;

import com.acme.backendunityvolunteer.application.dto.PagoDTO;
import com.acme.backendunityvolunteer.application.dto.UsuarioDTO;
import com.acme.backendunityvolunteer.domain.model.Pago;
import com.acme.backendunityvolunteer.domain.model.TipoSubscricion;
import com.acme.backendunityvolunteer.domain.model.TipoUsuario;
import com.acme.backendunityvolunteer.domain.model.Usuario;
import com.acme.backendunityvolunteer.domain.model.repository.PagoRepository;
import com.acme.backendunityvolunteer.domain.model.repository.UsuarioRepository;
import com.acme.backendunityvolunteer.interfaces.persistence.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PagoRepository pagoRepository;

    public PagoDTO registrarPago(PagoDTO pagoDTO) {
        Pago nuevoPago = new Pago();
        nuevoPago.setMonto(pagoDTO.getMonto());
        nuevoPago.setOrganizacionId(pagoDTO.getOrganizationId());

        try {
            nuevoPago.setTipoSubscricion(pagoDTO.getTipoSubscricion());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de usuario inválido.");
        }

        Pago pagoGuardado = pagoRepository.save(nuevoPago);
        return mapToDTO(pagoGuardado);
    }


    public PagoDTO obtenerPagoPorOrganizacionId(Long id) {
        Pago pago = pagoRepository.findByOrganizacionId(id)
                .orElseThrow(() -> new NotFoundException("Pago no encontrado"));

        return mapToDTO(pago);
    }
    public PagoDTO obtenerPagoPorId(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago no encontrado"));

        return mapToDTO(pago);
    }
    // Conversión de entidad a DTO
    private PagoDTO mapToDTO(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setId(pago.getId());
        dto.setOrganizationId(pago.getOrganizacionId());
        dto.setMonto(pago.getMonto());
        dto.setTipoSubscricion(pago.getTipoSubscricion());

        return dto;
    }
}