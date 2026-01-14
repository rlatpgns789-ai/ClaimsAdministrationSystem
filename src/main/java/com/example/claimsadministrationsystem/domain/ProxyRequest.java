package com.example.claimsadministrationsystem.domain;

import com.example.claimsadministrationsystem.domain.auditing.BaseTimeEntity;
import com.example.claimsadministrationsystem.domain.enums.ProxyRequestStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@NoArgsConstructor
@Getter
@Entity
public class ProxyRequest extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ProxyRequestStatus status;

    @Column(name = "total_missed_insurance_amount", nullable = false)
    private Long totalMissedInsuranceAmount;

    @Column(name = "commission_amount", nullable = false)
    private Long commissionAmount;


    @Builder
    public ProxyRequest(UUID userId, Long totalMissedInsuranceAmount, Long commissionAmount) {
        this.userId = userId;
        this.status = ProxyRequestStatus.PENDING;
        this.totalMissedInsuranceAmount = totalMissedInsuranceAmount;
        this.commissionAmount = commissionAmount;
    }


}
