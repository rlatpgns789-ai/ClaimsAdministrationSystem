package com.example.claimsadministrationsystem.feature.proxyrequest.repository;

import com.example.claimsadministrationsystem.domain.ProxyRequestUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProxyRequestUnitRepository extends JpaRepository<ProxyRequestUnit, UUID> {



}
