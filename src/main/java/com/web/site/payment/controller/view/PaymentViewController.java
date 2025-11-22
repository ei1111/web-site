package com.web.site.payment.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class PaymentViewController {

    @GetMapping("/admin")
    public String paymentMyPage() {
        return "payment/admin";
    }
}
