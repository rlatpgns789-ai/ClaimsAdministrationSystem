package com.example.claimsadministrationsystem.feature.proxyrequest.repository;

import com.example.claimsadministrationsystem.domain.ProxyRequestUnit;
import com.example.claimsadministrationsystem.domain.enums.ProxyRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ProxyRequestUnitRepository extends JpaRepository<ProxyRequestUnit, UUID> {

    boolean existsByProxyRequest_UserIdAndHospitalIdInAndProxyRequest_StatusIn(UUID userId, List<UUID> hospitalId, Collection<ProxyRequestStatus> statuses);

}
