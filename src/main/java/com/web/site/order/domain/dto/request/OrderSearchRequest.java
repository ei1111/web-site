package com.web.site.order.domain.dto.request;



import com.web.site.global.common.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class OrderSearchRequest {
    private String memberName;
    private OrderStatus orderStatus;
}
