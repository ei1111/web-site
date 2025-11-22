package com.web.site.payment.eventListener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaymentCancelEvent {
    private final String impUid;
}
