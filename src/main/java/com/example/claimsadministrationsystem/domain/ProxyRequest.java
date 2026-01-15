package com.example.claimsadministrationsystem.domain;

import com.example.claimsadministrationsystem.domain.auditing.BaseTimeEntity;
import com.example.claimsadministrationsystem.domain.enums.CoverageType;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "coverage_type", nullable = false)
    private CoverageType coverageType;

    private String reason;

    @Builder
    public ProxyRequest(UUID userId, Long totalMissedInsuranceAmount, Long commissionAmount, CoverageType coverageType) {
        this.userId = userId;
        this.status = ProxyRequestStatus.PENDING;
        this.totalMissedInsuranceAmount = totalMissedInsuranceAmount;
        this.commissionAmount = commissionAmount;
        this.coverageType = coverageType;
    }

    public void cancel() {
        this.status = this.status.cancel();
    }

    public void progress(){
        this.status = this.status.progress();
    }

    public void feeClaim(boolean isPrepaid){
        this.status = this.status.feeClaim(isPrepaid);
    }

    public void complete(){
        this.status = this.status.complete();
    }

    public void disclaimer(String reason){
        this.status = this.status.disclaimer();
        this.reason = reason;
    }


}
