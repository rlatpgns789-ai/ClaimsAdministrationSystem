package com.example.claimsadministrationsystem.feature.proxyrequest.service;

import com.example.claimsadministrationsystem.feature.proxyrequest.dto.CreateProxyRequestDto;
import com.example.claimsadministrationsystem.feature.proxyrequest.repository.ProxyRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProxyRequestService {

    private final ProxyRequestRepository proxyRequestRepository;


    public void createProxyRequest(CreateProxyRequestDto requestDto){
    }

    public void readProxyRequest(){

    }

    public void updateProxyRequest(){

    }

    public void cancelProxyRequest(){

    }



}
