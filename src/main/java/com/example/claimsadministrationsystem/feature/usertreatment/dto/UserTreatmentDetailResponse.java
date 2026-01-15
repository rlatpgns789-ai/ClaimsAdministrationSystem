package com.example.claimsadministrationsystem.feature.usertreatment.dto;

import com.example.claimsadministrationsystem.domain.UserTreatment;

import java.time.LocalDate;
import java.util.UUID;

public record UserTreatmentDetailResponse(
         UUID id,
         UUID hospitalId,
         UUID userId,
         String hospitalName,
         LocalDate treatmentDate,
         Long treatmentAmount
) {

    public static UserTreatmentDetailResponse from(UserTreatment userTreatment){
        return new UserTreatmentDetailResponse(userTreatment.getId(), userTreatment.getHospitalId(), userTreatment.getUserId(), userTreatment.getHospitalName(), userTreatment.getTreatmentDate(), userTreatment.getTreatmentAmount());
    }
}
