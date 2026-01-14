package com.example.claimsadministrationsystem.feature.usertreatment.repository;

import com.example.claimsadministrationsystem.domain.UserTreatment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserTreatmentRepository extends JpaRepository<UserTreatment, UUID> {
}
