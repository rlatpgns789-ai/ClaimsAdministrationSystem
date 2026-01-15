package com.example.claimsadministrationsystem.feature.proxyrequest.service;

import com.example.claimsadministrationsystem.common.error.InvalidProxyRequestStatusTransitionException;
import com.example.claimsadministrationsystem.domain.ProxyRequest;
import com.example.claimsadministrationsystem.domain.ProxyRequestUnit;
import com.example.claimsadministrationsystem.domain.UserTreatment;
import com.example.claimsadministrationsystem.domain.enums.CoverageType;
import com.example.claimsadministrationsystem.domain.enums.ProxyRequestStatus;
import com.example.claimsadministrationsystem.feature.proxyrequest.dto.CreateProxyRequestDto;
import com.example.claimsadministrationsystem.feature.proxyrequest.dto.ProxyRequestDetailResponse;
import com.example.claimsadministrationsystem.feature.proxyrequest.dto.ProxyRequestUpdateRequest;
import com.example.claimsadministrationsystem.feature.proxyrequest.repository.ProxyRequestRepository;
import com.example.claimsadministrationsystem.feature.proxyrequest.repository.ProxyRequestUnitRepository;
import com.example.claimsadministrationsystem.feature.usertreatment.repository.UserTreatmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Proxy;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProxyRequestService {

    private final ProxyRequestRepository proxyRequestRepository;

    private final UserTreatmentRepository userTreatmentRepository;

    private final ProxyRequestUnitRepository proxyRequestUnitRepository;


    public void createProxyRequest(CreateProxyRequestDto requestDto){

        UUID userId = requestDto.userId();

        validateCanRegisterProxyRequest(userId);

//        ProxyRequest proxyRequest = ProxyRequest.builder()
//                .userId(userId)
//                .totalMissedInsuranceAmount()
//                .commissionAmount()
//                .build();

//        proxyRequestRepository.save(proxyRequest);


    }

    public ProxyRequestDetailResponse readProxyRequest(UUID proxyRequestId){
        ProxyRequest proxyRequest = proxyRequestRepository.findById(proxyRequestId).orElseThrow(() -> new EntityNotFoundException("Proxy request not found"));

        return ProxyRequestDetailResponse.from(proxyRequest);
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


    public void validateCanRegisterProxyRequest(UUID userId){

        boolean existsActiveProxyRequest  = proxyRequestRepository.existsByUserIdAndStatusIn(userId, ProxyRequestStatus.activeStatuses());

        if (existsActiveProxyRequest){
            throw new IllegalStateException("Can not register proxy request");
        }
    }
    
    // 사용자 취소 된 병원의 경우 다시 신청 가능
    // 종결 또는 면책된 대행의 병원의 경우 다시 신청 불가능
    // 유저 ID로 병원 ID 조회 ->


    public void isReapplyHospital(){

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
