package com.web.site.member.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("USER", "일반 사용자"),
    ADMIN("ADMIN", "관리자");

    private final String code;
    private final String description;
}
