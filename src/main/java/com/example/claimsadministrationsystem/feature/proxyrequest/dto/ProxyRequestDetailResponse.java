package com.example.claimsadministrationsystem.feature.proxyrequest.dto;

import com.example.claimsadministrationsystem.domain.ProxyRequest;
import com.example.claimsadministrationsystem.domain.enums.ProxyRequestStatus;

import java.util.UUID;

public record ProxyRequestDetailResponse(
        UUID id,
        UUID userId,
        ProxyRequestStatus status,
        Long totalMissedInsuranceAmount,
        Long commissionAmount
) {
    public static ProxyRequestDetailResponse from(ProxyRequest proxyRequest){
        return new ProxyRequestDetailResponse(proxyRequest.getId(), proxyRequest.getUserId(),  proxyRequest.getStatus(), proxyRequest.getTotalMissedInsuranceAmount(), proxyRequest.getCommissionAmount());
    }
}
