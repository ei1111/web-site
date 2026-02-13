package com.web.site.board.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardPageResponse {

    private int pageNumber;
    private int startPage;
    private int endPage;
    private int totalPages;
    private long totalCount;
    private List<BoardResponse> boards;

    public BoardPageResponse(Page<BoardResponse> boardResponse) {
        this.pageNumber = boardResponse.getNumber();
        this.totalPages = boardResponse.getTotalPages();
        this.startPage = Math.max(1, pageNumber - 4);
        this.endPage = Math.min(boardResponse.getTotalPages(), pageNumber + 4);
        this.totalCount = boardResponse.getTotalElements();
        this.boards = boardResponse.getContent();
    }
}
