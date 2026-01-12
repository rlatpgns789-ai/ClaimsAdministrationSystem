package com.example.claimsadministrationsystem.domain;

import com.example.claimsadministrationsystem.domain.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
public class ProxyRequestUnit extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "hospital_id", nullable = false)
    UUID hospitalId;

    @Column(name = "unclaimed_insurance_amount", nullable = false)
    Long unclaimedInsuranceAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proxy_request_id")
    ProxyRequest proxyRequest;

}
