package com.web.site.notification.alarm;

import com.web.site.global.enums.NotificationType;
import com.web.site.notification.infrastructure.AbstractNotificationSender;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationSender extends AbstractNotificationSender {

    @Override
    protected void doSend(String target, String message) {
        System.out.println("[SMS] " + target + " 에게 문자 발송: " + message);
    }

    @Override
    public NotificationType getType() {
        return NotificationType.SMS;
    }
}
