package com.example.claimsadministrationsystem.domain;

import com.example.claimsadministrationsystem.domain.auditing.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
public class UserTreatment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "hospital_id", nullable = false)
    private UUID hospitalId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "hospital_name", nullable = false, length = 50)
    private String hospitalName;

    @Column(name = "treatment_date", nullable = false)
    private LocalDate treatmentDate;

    @Column(name = "treatment_amount", nullable = false)
    private Long treatmentAmount;

}
