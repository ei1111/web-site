package com.web.site.notification.dto;

import com.web.site.global.enums.NotificationType;

public class EmailResponse extends NotificationResponse {
    private final Long emailId;

    public EmailResponse(Long userId, NotificationType type, Long emailId) {
        super(userId, type);
        this.emailId = emailId;
    }
}
