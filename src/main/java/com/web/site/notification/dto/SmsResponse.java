package com.web.site.notification.dto;

import com.web.site.global.enums.NotificationType;

public class SmsResponse extends NotificationResponse {
    private final Long smsId;

    public SmsResponse(Long userId, NotificationType type, Long smsId) {
        super(userId, type);
        this.smsId = smsId;
    }
}
