package com.web.site.notification.domain.dto;

import com.web.site.global.audit.BaseEntity;
import com.web.site.global.audit.BaseTimeEntity;
import com.web.site.global.enums.NotificationType;
import java.time.Instant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class NotificationResponse  {
    public Long userId;
    public NotificationType type;
}
