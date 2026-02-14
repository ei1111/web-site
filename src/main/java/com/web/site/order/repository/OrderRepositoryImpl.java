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
import com.web.site.payment.domain.entity.QPayment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private QOrder order = QOrder.order;
    private QMember member = QMember.member;
    private QPayment payment = QPayment.payment;


    @Override
    public List<OrderResponse> orderSearch(OrderSearchRequest request, String userId) {
        Role role = findRoleByUserId(userId);

        return jpaQueryFactory.selectFrom(order)
                .join(order.member, member)
                .join(order.payment, payment)
                .where(
                        applyRoleScope(role, userId)
                      , eqStatus(request.getOrderStatus())
                      , likeMember(request.getMemberName())
                )
                .fetch()
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    private Role findRoleByUserId(String userId) {
        return jpaQueryFactory
                .select(member.role)
                .from(member)
                .where(member.userId.eq(userId))
                .fetchOne();
    }

    private BooleanExpression likeMember(String memberName) {
        return StringUtils.hasText(memberName) ? QMember.member.name.contains(memberName) : null;
    }

    private BooleanExpression eqStatus(OrderStatus status) {
        return status != null ? QOrder.order.status.eq(status) : null;
    }

    private BooleanExpression applyRoleScope(Role role, String userId) {
        if (role == Role.ADMIN) {
            return null;
        }
        return restrictToRequester(userId);
    }

    private BooleanExpression restrictToRequester(String userId) {
        return member.userId.eq(userId);
    }
}