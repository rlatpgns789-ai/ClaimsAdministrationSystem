package com.example.claimsadministrationsystem.feature.proxyrequest.repository;

import com.example.claimsadministrationsystem.domain.ProxyRequest;
import com.example.claimsadministrationsystem.domain.enums.ProxyRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;


public interface ProxyRequestRepository extends JpaRepository<ProxyRequest, UUID> {

    boolean existsByUserIdAndStatusIn(UUID userId, Collection<ProxyRequestStatus> statuses);

}
