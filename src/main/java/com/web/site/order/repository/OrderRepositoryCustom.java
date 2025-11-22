package com.web.site.order.repository;


import com.web.site.order.domain.dto.request.OrderSearchRequest;
import com.web.site.order.domain.dto.response.OrderResponse;
import java.util.List;

public interface OrderRepositoryCustom {
    List<OrderResponse> orderSearch(OrderSearchRequest request);
}
