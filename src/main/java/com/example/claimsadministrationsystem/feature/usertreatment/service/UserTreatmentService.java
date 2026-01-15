package com.example.claimsadministrationsystem.feature.usertreatment.service;

import com.example.claimsadministrationsystem.domain.UserTreatment;
import com.example.claimsadministrationsystem.feature.usertreatment.repository.UserTreatmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserTreatmentService {

    private final UserTreatmentRepository userTreatmentRepository;

    public UserTreatment readUserTreatment(UUID userId){
        UserTreatment userTreatment = userTreatmentRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User does not exist"));

        return userTreatment;
    }
}
