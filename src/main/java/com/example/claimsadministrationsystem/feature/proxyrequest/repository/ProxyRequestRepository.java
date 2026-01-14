package com.example.claimsadministrationsystem.feature.proxyrequest.repository;

import com.example.claimsadministrationsystem.domain.ProxyRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ProxyRequestRepository extends JpaRepository<ProxyRequest, UUID> {


}
