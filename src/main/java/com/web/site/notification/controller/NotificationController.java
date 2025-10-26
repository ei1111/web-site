package com.web.site.notification.controller;

import com.web.site.global.enums.NotificationType;
import com.web.site.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notify")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/{type}")
    public String send(
            @PathVariable NotificationType type,
            @RequestParam String target,
            @RequestParam String message) {

        notificationService.notifyUser(type, target, message);
        return "Sent " + type;
    }
}
