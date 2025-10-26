package com.web.site.notification.service;

import com.web.site.global.enums.NotificationType;
import com.web.site.notification.infrastructure.NotificationSender;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final Map<NotificationType, NotificationSender> senderMap;

    // 스프링이 모든 구현체를 자동 주입해줌
    @Autowired
    public NotificationService(List<NotificationSender> senders) {
        this.senderMap = senders.stream()
                .collect(Collectors.toMap(NotificationSender::getType, Function.identity()));
    }

    public void notifyUser(NotificationType type, String target, String message) {
        NotificationSender sender = senderMap.get(type);
        if (sender == null) {
            throw new IllegalArgumentException("지원하지 않는 알림 타입: " + type);
        }
        sender.send(target, message);
    }
}
