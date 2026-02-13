package com.web.site.board.domain.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public void setRowNum(Integer rowNum) {  // package-private
        this.rowNum = rowNum;
    }
}
