package com.web.site.order.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.site.global.common.enums.OrderStatus;
import com.web.site.global.common.util.SecurityUtill;
import com.web.site.global.enums.Role;
import com.web.site.member.domain.entity.QMember;
import com.web.site.order.domain.dto.request.OrderSearchRequest;
import com.web.site.order.domain.dto.response.OrderResponse;
import com.web.site.order.domain.entity.QOrder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OrderResponse> orderSearch(OrderSearchRequest request) {
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        return jpaQueryFactory.selectFrom(order)
                .join(order.member, member)
                .where(
                        likeMember(request.getMemberName())
                      , eqStatus(request.getOrderStatus())
                      , eqMemberId()
                )
                .fetch()
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    private BooleanExpression likeMember(String memberName) {
        return StringUtils.hasText(memberName) ? QMember.member.name.like(memberName) : null;
    }

    private BooleanExpression eqStatus(OrderStatus status) {
        return status != null ? QOrder.order.status.eq(status) : null;
    }

    private BooleanExpression eqMemberId() {
        return Role.ADMIN.equals(SecurityUtill.getUserId()) ? null : QMember.member.userId.eq(SecurityUtill.getUserId());
    }
}