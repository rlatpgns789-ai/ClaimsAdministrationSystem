package com.example.claimsadministrationsystem.feature.usertreatment.controller;

import com.example.claimsadministrationsystem.common.dto.ResponseDto;
import com.example.claimsadministrationsystem.feature.usertreatment.dto.UserTreatmentDetailResponse;
import com.example.claimsadministrationsystem.feature.usertreatment.service.UserTreatmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserTreatmentController {

    private final UserTreatmentService userTreatmentService;

    @GetMapping("/{userId}/treatments")
    public ResponseEntity<ResponseDto<UserTreatmentDetailResponse>> getUserTreatment(@PathVariable UUID userId) {
        return ResponseEntity.ok(new ResponseDto<>(true,  UserTreatmentDetailResponse.from(userTreatmentService.readUserTreatment(userId))));
    }
}
