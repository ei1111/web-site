package com.web.site.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 회원 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "회원을 찾을 수 없습니다."),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "이미 존재하는 이메일입니다."),
    EXIST_USER_ID(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "존재하는 아이디입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "비밀번호가 일치하지 않습니다."),

    // 인증 관련 에러
    UNAUTHORIZED(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "인증되지 않은 접근입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "만료된 토큰입니다."),

    // 권한 관련 에러 (3000번대)
    ACCESS_DENIED(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "접근 권한이 없습니다."),

    // 입력값 검증 에러 (4000번대)
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘못된 입력값입니다."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘못된 이메일 형식입니다."),

    // 서버 에러 (5000번대)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CONFLICT", "서버 내부 오류가 발생했습니다.");


    ErrorCode(HttpStatus httpStatus, String error, String message) {
        this.httpStatus = httpStatus;
        this.error = error;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String error;
    private String message;
} 