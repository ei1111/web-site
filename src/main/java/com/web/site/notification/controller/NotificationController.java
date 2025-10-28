package com.web.site.notification.controller;

import com.web.site.global.enums.NotificationType;
import com.web.site.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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
