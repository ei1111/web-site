package com.web.site.order.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class OrderViewController {
    @GetMapping("/orderList")
    public String orderList() {
        return "order/orderList";
    }
}
