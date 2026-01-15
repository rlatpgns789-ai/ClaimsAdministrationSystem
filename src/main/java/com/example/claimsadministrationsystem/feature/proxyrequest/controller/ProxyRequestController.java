package com.example.claimsadministrationsystem.feature.proxyrequest.controller;

import com.example.claimsadministrationsystem.common.dto.ResponseDto;
import com.example.claimsadministrationsystem.feature.proxyrequest.dto.CreateProxyRequestDto;
import com.example.claimsadministrationsystem.feature.proxyrequest.dto.ProxyRequestDetailResponse;
import com.example.claimsadministrationsystem.feature.proxyrequest.dto.ProxyRequestUpdateRequest;
import com.example.claimsadministrationsystem.feature.proxyrequest.service.ProxyRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/proxy-requests")
@RequiredArgsConstructor
public class ProxyRequestController {

    private final ProxyRequestService proxyRequestService;

    @PostMapping("")
    public ResponseEntity<ResponseDto<UUID>> registerProxyRequest(@RequestBody CreateProxyRequestDto requestDto) {

        return ResponseEntity.ok(new ResponseDto<>(true, proxyRequestService.createProxyRequest(requestDto)));
    }


    @PatchMapping("/{proxyRequestId}/status")
    public ResponseEntity<ResponseDto<UUID>> updateProxyRequest(@PathVariable UUID proxyRequestId, @RequestBody ProxyRequestUpdateRequest request) {
        return ResponseEntity.ok(new ResponseDto<>(true, proxyRequestService.updateProxyRequest(proxyRequestId, request)));
    }

    @DeleteMapping("/{proxyRequestId}")
    public ResponseEntity<ResponseDto> cancelProxyRequest(@PathVariable UUID proxyRequestId) {

        proxyRequestService.cancelProxyRequest(proxyRequestId);
        return ResponseEntity.ok(new ResponseDto<>(true, null));
    }

    @GetMapping("/{proxyRequestId}")
    public ResponseEntity<ResponseDto<ProxyRequestDetailResponse>> getProxyRequest(@PathVariable UUID proxyRequestId) {

        return ResponseEntity.ok(new ResponseDto<>(true, ProxyRequestDetailResponse.from(proxyRequestService.readProxyRequest(proxyRequestId))));
    }


}
