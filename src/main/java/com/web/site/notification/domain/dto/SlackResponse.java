package com.web.site.notification.domain.dto;

import com.web.site.global.enums.NotificationType;

public class SlackResponse extends NotificationResponse {

    private final Long slackId;


    public SlackResponse(Long userId, NotificationType type, Long slackId) {
        super(userId, type);
        this.slackId = slackId;
    }
}
