package com.example.claimsadministrationsystem.feature.proxyrequest.service;

import com.example.claimsadministrationsystem.domain.ProxyRequest;
import com.example.claimsadministrationsystem.domain.ProxyRequestUnit;
import com.example.claimsadministrationsystem.domain.UserTreatment;
import com.example.claimsadministrationsystem.domain.enums.ProxyRequestStatus;
import com.example.claimsadministrationsystem.feature.proxyrequest.dto.CreateProxyRequestDto;
import com.example.claimsadministrationsystem.feature.proxyrequest.repository.ProxyRequestRepository;
import com.example.claimsadministrationsystem.feature.proxyrequest.repository.ProxyRequestUnitRepository;
import com.example.claimsadministrationsystem.feature.usertreatment.repository.UserTreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void readProxyRequest(){

    }

    public void updateProxyRequest(){

    }

    public void cancelProxyRequest(){

    }


    public void validateCanRegisterProxyRequest(UUID userId){

        // 전체를 불러와서 하는게 낫겠다 그지? 그러면 문제가 되는게 CANCEL, DISCLAIMER, COMPLETED

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
