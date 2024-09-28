package com.acme.backendunityvolunteer.application.dto.user_management;

import com.acme.backendunityvolunteer.domain.model.Logros;
import com.acme.backendunityvolunteer.domain.model.repository.LogroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogroService {

    @Autowired
    private LogroRepository logroRepository;

    public List<Logros> obtenerTodosLosLogros() {
        return logroRepository.findAll();
    }

    public Logros crearLogro(Logros logro) {
        return logroRepository.save(logro);
    }
}