package com.example.claimsadministrationsystem.feature.proxyrequest.service;

import com.example.claimsadministrationsystem.common.error.InvalidProxyRequestStateException;
import com.example.claimsadministrationsystem.common.error.InvalidProxyRequestStatusTransitionException;
import com.example.claimsadministrationsystem.domain.ProxyRequest;
import com.example.claimsadministrationsystem.domain.ProxyRequestUnit;
import com.example.claimsadministrationsystem.domain.UserTreatment;
import com.example.claimsadministrationsystem.domain.enums.ProxyRequestStatus;
import com.example.claimsadministrationsystem.feature.proxyrequest.dto.CreateProxyRequestDto;
import com.example.claimsadministrationsystem.feature.proxyrequest.dto.HospitalAmount;
import com.example.claimsadministrationsystem.feature.proxyrequest.dto.ProxyRequestDetailResponse;
import com.example.claimsadministrationsystem.feature.proxyrequest.dto.ProxyRequestUpdateRequest;
import com.example.claimsadministrationsystem.feature.proxyrequest.repository.ProxyRequestRepository;
import com.example.claimsadministrationsystem.feature.proxyrequest.repository.ProxyRequestUnitRepository;
import com.example.claimsadministrationsystem.feature.usertreatment.repository.UserTreatmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProxyRequestService {

    private final ProxyRequestRepository proxyRequestRepository;

    private final ProxyRequestUnitRepository proxyRequestUnitRepository;
    private final UserTreatmentRepository userTreatmentRepository;


    @Transactional
    public UUID createProxyRequest(CreateProxyRequestDto requestDto) {

        UUID userId = requestDto.userId();
        List<UUID> hospitalIds = requestDto.hospitalList().stream()
                .distinct()
                .toList();

        checkCanRegisterUser(userId);
        checkCanApplyForHospitals(userId, hospitalIds);

        List<HospitalAmount> rows =
                userTreatmentRepository.sumTreatmentAmountByHospitalIds(userId, hospitalIds);

        Map<UUID, Long> amountByHospital = new HashMap<>();
        long totalMissed = 0L;

        for (HospitalAmount r : rows) {
            amountByHospital.put(r.hospitalId(), r.amount());
            totalMissed += r.amount();
        }

        ProxyRequest proxyRequest = ProxyRequest.builder()
                .userId(userId)
                .coverageType(requestDto.coverageType())
                .totalMissedInsuranceAmount(totalMissed)
                .commissionAmount(requestDto.coverageType().calculateCommission(totalMissed))
                .build();

        List<ProxyRequestUnit> units = hospitalIds.stream()
                .map(hospitalId -> ProxyRequestUnit.builder()
                        .hospitalId(hospitalId)
                        .unclaimedInsuranceAmount(
                                amountByHospital.getOrDefault(hospitalId, 0L)
                        )
                        .proxyRequest(proxyRequest)
                        .build())
                .toList();

        proxyRequestRepository.save(proxyRequest);
        proxyRequestUnitRepository.saveAll(units);

        return proxyRequest.getId();
    }

    public ProxyRequest readProxyRequest(UUID proxyRequestId){
        ProxyRequest proxyRequest = proxyRequestRepository.findById(proxyRequestId).orElseThrow(() -> new EntityNotFoundException("Proxy request not found"));

        return proxyRequest;
    }

    @Transactional
    public UUID updateProxyRequest(UUID proxyRequestId, ProxyRequestUpdateRequest request) {
        ProxyRequest proxyRequest = proxyRequestRepository.findById(proxyRequestId).orElseThrow(() -> new EntityNotFoundException("Proxy request not found"));

        ProxyRequestStatus target = request.targetStatus();

        switch (proxyRequest.getStatus()) {
            case ProxyRequestStatus.PENDING:
                if (target == ProxyRequestStatus.DISCLAIMER) {
                    proxyRequest.disclaimer(request.reason());
                } else if (target == ProxyRequestStatus.IN_PROGRESS) {
                    proxyRequest.progress();
                } else {
                    throw new InvalidProxyRequestStatusTransitionException(
                            "Invalid targetStatus for PENDING: " + target
                    );
                }
                break;
            case ProxyRequestStatus.IN_PROGRESS:
                if (target == ProxyRequestStatus.DISCLAIMER) {
                    proxyRequest.disclaimer(request.reason());
                } else if (target == ProxyRequestStatus.FEE_CLAIM) {
                    proxyRequest.feeClaim(proxyRequest.getCoverageType().isPrepaid());
                } else {
                    throw new InvalidProxyRequestStatusTransitionException(
                            "Invalid targetStatus for IN_PROGRESS: " + target
                    );
                }
                break;
            case ProxyRequestStatus.FEE_CLAIM:
                if (target == ProxyRequestStatus.COMPLETED) {
                    proxyRequest.complete();
                } else {
                    throw new InvalidProxyRequestStatusTransitionException(
                            "Invalid targetStatus for FEE_CLAIM: " + target
                    );
                }
                break;
            default:
                throw new InvalidProxyRequestStatusTransitionException("can not update proxy request status. check status. status: " + proxyRequest.getStatus());
        }

        return proxyRequest.getId();
    }


    @Transactional
    public void cancelProxyRequest(UUID proxyRequestId){

        ProxyRequest proxyRequest = proxyRequestRepository.findById(proxyRequestId).orElseThrow(() -> new EntityNotFoundException("Proxy request not found"));

        proxyRequest.cancel();

    }
    public void checkCanApplyForHospitals(UUID userId, List<UUID> hospitalIds) {
        boolean exists = proxyRequestUnitRepository
                .existsByProxyRequest_UserIdAndHospitalIdInAndProxyRequest_StatusIn
                        (
                        userId,
                        hospitalIds,
                        Set.of(ProxyRequestStatus.COMPLETED, ProxyRequestStatus.DISCLAIMER)
                );

        if (exists) {
            throw new InvalidProxyRequestStateException("Contains hospitals that cannot be reapplied.");
        }
    }


    public void checkCanRegisterUser(UUID userId){

        boolean existsActiveProxyRequest  = proxyRequestRepository.existsByUserIdAndStatusIn(userId, ProxyRequestStatus.activeStatuses());

        if (existsActiveProxyRequest){
            throw new InvalidProxyRequestStateException("Can not register proxy request");
        }
    }


    public void createProxyRequestUnit(UserTreatment userTreatment, ProxyRequest proxyRequest){
        ProxyRequestUnit proxyRequestUnit = ProxyRequestUnit.builder()
                .hospitalId(userTreatment.getHospitalId())
                .unclaimedInsuranceAmount(userTreatment.getTreatmentAmount())
                .proxyRequest(proxyRequest)
                .build();

        proxyRequestUnitRepository.save(proxyRequestUnit);
    }
}
