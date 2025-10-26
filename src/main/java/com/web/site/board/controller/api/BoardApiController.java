package com.web.site.board.controller.api;


import com.web.site.board.form.BoardPageResponse;
import com.web.site.board.form.BoardRequest;
import com.web.site.board.form.BoardResponse;
import com.web.site.board.service.BoardService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/v1")
@Tag(name = "게시판 CRUD API")
@OpenAPIDefinition(info = @Info(title = "쇼핑물 API", description = "쇼핑몰 서버에서 제공하는 각종 메타정보 API", version = "v1"))
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/list")
    @Operation(summary = "게시판 작성 리스트 조회 API", description =  "게시판 작성 리스트 조회 API")
    public BoardPageResponse list(@PageableDefault Pageable pageable, @RequestParam(required = false) String searchText) {
        return boardService.findAll(searchText, pageable);
    }

    @GetMapping("/form")
    @Operation(summary = "게시판 글 조회 API", description = "게시판 글 조회 API")
    public BoardResponse form(@RequestParam(required = false) @Parameter(example = "1") Long boardId) {
        return Optional.ofNullable(boardId)
                .map(s -> boardService.findById(s))
                .orElseGet(BoardResponse::new);
    }


    @PostMapping("/form")
    @Operation(summary = "게시판 글 등록 API" , description = "게시판 글 등록 API")
    public void save(@Valid @RequestBody BoardRequest board) {
        boardService.save(board);
    }

    @PutMapping("/form")
    @Operation(summary = "게시판 글 수정 API", description = "게시판 글 수정 API")
    public void update(@RequestBody BoardRequest board) {
        boardService.update(board);
    }
}
