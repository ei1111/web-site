package com.web.site.notification.infrastructure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractNotificationSender implements NotificationSender {

    @Override
    public void send(String target, String message) {
        try {
            validate(target, message);
            log.info("📤 [{}] 발송 시작", getType());
            doSend(target, message);
            log.info("✅ [{}] 발송 완료", getType());
        } catch (Exception e) {
            log.error("❌ [{}] 발송 실패: {}", getType(), e.getMessage());
            handleError(e);
        }
    }

    protected void validate(String target, String message) {
        if (target == null || message == null) {
            throw new IllegalArgumentException("대상과 메시지는 필수입니다.");
        }
    }

    protected abstract void doSend(String target, String message);

    protected void handleError(Exception e) {
        // 공통 예외 처리 로직 (ex: Slack 알림 전송)
    }
}
