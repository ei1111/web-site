package com.web.site.notification.alarm;

import com.web.site.global.enums.NotificationType;
import com.web.site.notification.infrastructure.AbstractNotificationSender;
import org.springframework.stereotype.Component;

@Component
public class SlackNotificationSender extends AbstractNotificationSender {

    @Override
    protected void doSend(String target, String message) {
        System.out.println("[SLACK] " + target + " 채널로 메시지 전송: " + message);
    }

    @Override
    public NotificationType getType() {
        return NotificationType.SLACK;
    }
}