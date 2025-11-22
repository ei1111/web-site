package com.web.site.order.service;


import com.web.site.delivery.domain.entity.Delivery;
import com.web.site.global.common.util.SecurityUtill;
import com.web.site.item.domain.entity.Item;
import com.web.site.item.repository.ItemRepository;
import com.web.site.member.domain.entity.Member;
import com.web.site.member.repository.MemberRepository;
import com.web.site.order.domain.dto.request.OrderSearchRequest;
import com.web.site.order.domain.dto.response.OrderResponse;
import com.web.site.order.domain.entity.Order;
import com.web.site.order.repository.OrderRepository;
import com.web.site.orderItem.domain.entity.OrderItem;
import com.web.site.payment.domain.entity.Payment;
import com.web.site.payment.facade.CancelFacadeEvent;
import com.web.site.payment.repository.PaymentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final CancelFacadeEvent cancelFacadeEvent;


    @Transactional
    public void order(Long itemId, int count) {
        //비관적락으로 재고 감소 문제 해결
        Item item = itemRepository.findByIdPerssimsticLock(itemId).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findByUserId(SecurityUtill.getUserId()).orElseThrow(IllegalArgumentException::new);

        Delivery delivery = new Delivery(member.getAddress());
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.from(member, delivery, orderItem);

        orderRepository.save(order);
    }

    public List<OrderResponse> orderSearch(OrderSearchRequest request) {
        return orderRepository.orderSearch(request);
    }

    @Transactional
    public void cancel(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        order.cancel();

        paymentRepository.findByOrder(order)
                .map(Payment::getImpUid)
                .ifPresent(impUid -> {
                    cancelFacadeEvent.cancelEvent(impUid);
                });
    }
}
