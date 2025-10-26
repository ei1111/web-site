package com.web.site.notification.infrastructure;

import com.web.site.global.enums.NotificationType;

public interface NotificationSender {
    void send(String target, String message);
    NotificationType getType();
}
