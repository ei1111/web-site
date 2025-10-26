package com.web.site.notification.alarm;

import com.web.site.global.enums.NotificationType;
import com.web.site.notification.infrastructure.AbstractNotificationSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationSender extends AbstractNotificationSender {

    @Override
    protected void doSend(String target, String message) {
        System.out.println("[EMAIL] " + target + " 에게 메일 발송: " + message);
    }

    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }
}
