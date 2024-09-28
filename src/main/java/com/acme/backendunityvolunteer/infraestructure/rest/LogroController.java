package com.acme.backendunityvolunteer.infraestructure.rest;

import com.acme.backendunityvolunteer.application.dto.user_management.LogroService;
import com.acme.backendunityvolunteer.domain.model.Logros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logros")
public class LogroController {

    @Autowired
    private LogroService logroService;

    @GetMapping
    public ResponseEntity<List<Logros>> obtenerTodosLosLogros() {
        List<Logros> logros = logroService.obtenerTodosLosLogros();
        return ResponseEntity.ok(logros);
    }

    @PostMapping
    public ResponseEntity<Logros> crearLogro(@RequestBody Logros logro) {
        Logros nuevoLogro = logroService.crearLogro(logro);
        return ResponseEntity.ok(nuevoLogro);
    }
}