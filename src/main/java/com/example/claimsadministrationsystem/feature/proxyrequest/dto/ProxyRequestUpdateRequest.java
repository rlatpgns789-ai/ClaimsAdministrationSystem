package com.example.claimsadministrationsystem.feature.proxyrequest.dto;

import com.example.claimsadministrationsystem.domain.enums.ProxyRequestStatus;

public record ProxyRequestUpdateRequest(

        ProxyRequestStatus targetStatus,

        String reason
) {
}
