package com.web.site.board.domain.dto;


import com.querydsl.core.annotations.QueryProjection;
import com.web.site.board.domain.entity.Board;
import com.web.site.global.common.util.SecurityUtill;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardResponse {
    public Long boardId;
    public String title;
    public String content;
    public String userId;
    public Integer rowNum;

    @QueryProjection
    public BoardResponse(Long boardId, String title, String content, String userId) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
