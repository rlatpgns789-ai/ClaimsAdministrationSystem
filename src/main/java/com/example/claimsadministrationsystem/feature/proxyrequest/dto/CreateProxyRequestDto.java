package com.example.claimsadministrationsystem.feature.proxyrequest.dto;

import com.example.claimsadministrationsystem.domain.enums.CoverageType;

import java.util.List;
import java.util.UUID;

public record CreateProxyRequestDto(
        UUID userId,
        CoverageType coverageType,
        List<UUID> hospitalList
) {
}
