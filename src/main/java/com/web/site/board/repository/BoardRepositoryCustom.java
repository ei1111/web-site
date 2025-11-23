package com.web.site.board.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.site.board.domain.dto.response.BoardResponse;
import com.web.site.board.domain.dto.response.QBoardResponse;
import com.web.site.board.domain.entity.QBoard;
import com.web.site.member.domain.entity.QMember;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QBoard board = QBoard.board;
    private final QMember member = QMember.member;

    public Page<BoardResponse> findByTitleContainingOrContentContaining(String serachWord, Pageable pageable) {

        AtomicInteger index = new AtomicInteger((int) pageable.getOffset() + 1);

        List<BoardResponse> boardResponse = jpaQueryFactory
                .select(
                        new QBoardResponse(
                                board.id,
                                board.title,
                                board.content,
                                member.userId
                        )
                )
                .from(board)
                .leftJoin(board.member, member)
                .where(titleEq(serachWord))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdDate.desc())
                .fetch()
                .stream()
                .peek(s -> s.setRowNum(index.getAndIncrement()))
                .toList();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(board.count())
                .from(board)
                .leftJoin(board.member, member)
                .where(titleEq(serachWord))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(boardResponse, pageable, countQuery::fetchCount);
    }

    private BooleanBuilder titleEq(String serachWord) {
        return nullSafeBuilder(() -> board.title.contains(serachWord), serachWord);
    }

    public static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> supplier, String keyword) {
        return StringUtils.hasText(keyword) ? new BooleanBuilder(supplier.get()) : new BooleanBuilder();
    }
}
