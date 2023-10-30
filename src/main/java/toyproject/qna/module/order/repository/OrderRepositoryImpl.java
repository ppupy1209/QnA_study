package toyproject.qna.module.order.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import toyproject.qna.module.delivery.entity.QDelivery;
import toyproject.qna.module.member.entity.QMember;
import toyproject.qna.module.order.entity.Order;
import toyproject.qna.module.order.entity.QOrder;

import javax.persistence.EntityManager;
import java.util.List;

import static toyproject.qna.module.delivery.entity.QDelivery.*;
import static toyproject.qna.module.member.entity.QMember.*;
import static toyproject.qna.module.order.entity.QOrder.*;

public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Order> findOrdersWithMemberAndDelivery(Pageable pageable) {
        QueryResults<Order> results = queryFactory.select(order)
                .from(order)
                .join(order.member, member)
                .fetchJoin()
                .join(order.delivery, delivery)
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Order> orders = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(orders,pageable,total);
    }

    @Override
    public List<Order> findOrdersByMemberId(Long memberId) {
        return queryFactory.select(order)
                .from(order)
                .join(order.member, member)
                .fetchJoin()
                .join(order.delivery, delivery)
                .fetchJoin()
                .where(member.id.eq(memberId))
                .fetch();
    }
}
