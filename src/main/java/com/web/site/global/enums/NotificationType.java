package com.web.site.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
     EMAIL("이메일")
    , SMS("문자")
    , SLACK("슬랙");


    private final String description;
}
