package com.web.site.order.service;


import com.web.site.order.domain.entity.Delivery;
import com.web.site.global.redis.RedisKeyPrefix;
import com.web.site.global.redis.RedisManager;
import com.web.site.item.domain.entity.Item;
import com.web.site.item.repository.ItemRepository;
import com.web.site.member.domain.entity.Member;
import com.web.site.member.repository.MemberRepository;
import com.web.site.order.domain.dto.request.OrderRequest;
import com.web.site.order.domain.dto.request.OrderSearchRequest;
import com.web.site.order.domain.dto.response.OrderResponse;
import com.web.site.order.domain.entity.Order;
import com.web.site.order.repository.OrderRepository;
import com.web.site.orderItem.domain.entity.OrderItem;
import com.web.site.payment.domain.entity.Payment;
import com.web.site.payment.facade.CancelFacadeEvent;
import com.web.site.payment.repository.PaymentRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final CancelFacadeEvent cancelFacadeEvent;
    private final RedisManager redisManager;


    @Transactional
    public void order(OrderRequest request, String userId) {
        Long itemId = request.itemId();
        //비관적락으로 재고 감소 문제 해결
        Item item = itemRepository.findByIdPerssimsticLock(itemId).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(IllegalArgumentException::new);

        Delivery delivery = new Delivery(member.getAddress());

        int count = request.count();
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.from(member, delivery, orderItem);

        //레디스 제거
        redisManager.delete(RedisKeyPrefix.ITEM_DETAIL, itemId);

        orderRepository.save(order);
    }

    public List<OrderResponse> orderSearch(OrderSearchRequest request, String userId) {
        return orderRepository.orderSearch(request, userId);
    }

    @Transactional
    public void cancel(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        order.cancel();

        Long itemId = order.getOrderItems().stream()
                .findFirst()
                .map(orderItem -> orderItem.getItem().getId())
                .orElseThrow(() -> new NoSuchElementException("OrderItem not found"));

        redisManager.delete(RedisKeyPrefix.ITEM_DETAIL, itemId);

        paymentRepository.findByOrder(order)
                .map(Payment::getImpUid)
                .ifPresent(impUid -> {
                    cancelFacadeEvent.cancelEvent(impUid);
                });
    }
}
