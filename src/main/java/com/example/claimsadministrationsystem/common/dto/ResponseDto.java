package com.example.claimsadministrationsystem.common.dto;

import lombok.Getter;


@Getter
public class ResponseDto<T> {

    private final Boolean success;
    private T content;

    public ResponseDto(Boolean success, T content) {
        this.success = success;
        this.content = content;
    }

    public ResponseDto(Boolean success){
        this.success = success;
    }
}
