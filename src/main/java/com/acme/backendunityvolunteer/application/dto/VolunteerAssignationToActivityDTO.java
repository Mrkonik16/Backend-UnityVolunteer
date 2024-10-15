package com.acme.backendunityvolunteer.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VolunteerAssignationToActivityDTO {
    private Long id;
    private Long organizacionId;
    private Long voluntarioId;
    private Long actividadId;
}
