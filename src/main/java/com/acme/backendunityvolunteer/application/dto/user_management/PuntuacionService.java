package com.acme.backendunityvolunteer.application.dto.user_management;

import com.acme.backendunityvolunteer.domain.model.PerfilVoluntario;
import com.acme.backendunityvolunteer.domain.model.Puntuacion;
import com.acme.backendunityvolunteer.domain.model.repository.ActividadRepository;
import com.acme.backendunityvolunteer.domain.model.repository.PuntuacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.acme.backendunityvolunteer.domain.model.repository.PerfilVoluntarioRepository;
import java.time.LocalDate;
import java.util.List;

@Service
public class PuntuacionService {


    @Autowired
    private PuntuacionRepository puntuacionRepository;

    @Autowired
    private PerfilVoluntarioRepository voluntarioRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    public Puntuacion addPuntuacion(Long voluntarioId, Long actividadId, int calificacion, String comentario) {
        Puntuacion puntuacion = new Puntuacion();
        PerfilVoluntario voluntario = voluntarioRepository.findById(voluntarioId).orElseThrow();

        puntuacion.setVoluntario(voluntario);
        puntuacion.setActividad(actividadRepository.findById(actividadId).orElseThrow());
        puntuacion.setCalificacion(calificacion);
        puntuacion.setComentario(comentario);
        puntuacion.setFecha(LocalDate.now());

        Puntuacion savedPuntuacion = puntuacionRepository.save(puntuacion);

        // Update average score
        updateVoluntarioPuntuacionPromedio(voluntario);

        return savedPuntuacion;
    }

    private void updateVoluntarioPuntuacionPromedio(PerfilVoluntario voluntario) {
        List<Puntuacion> puntuaciones = puntuacionRepository.findByVoluntario(voluntario);
        double average = puntuaciones.stream().mapToInt(Puntuacion::getCalificacion).average().orElse(0.0);
        voluntario.setPuntuacionPromedio(average);
        voluntarioRepository.save(voluntario);
    }
}