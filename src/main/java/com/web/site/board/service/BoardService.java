package com.web.site.board.service;


import com.web.site.board.entity.Board;
import com.web.site.board.form.BoardPageResponse;
import com.web.site.board.form.BoardRequest;
import com.web.site.board.form.BoardResponse;
import com.web.site.board.repository.BoardRepository;
import com.web.site.board.repository.BoardRepositoryCustom;
import com.web.site.global.common.util.SecurityUtill;
import com.web.site.global.redis.RedisKeyPrefix;
import com.web.site.global.redis.RedisManager;
import com.web.site.member.domain.entity.Member;
import com.web.site.member.service.MemberService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardRepositoryCustom boardRepositoryCustom;
    private final MemberService memberService;
    private final RedisManager redisManager;

    public BoardPageResponse findAll(String searchText, Pageable pageable) {
        Page<BoardResponse> boardResponse = boardRepositoryCustom.findByTitleContainingOrContentContaining(searchText, pageable);
        return new BoardPageResponse(boardResponse);
    }

    public BoardResponse findById(Long id) {
        return Optional.ofNullable(redisGet(id))
                .orElseGet(() -> {
                    Board board = boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);
                    BoardResponse boardResponse = BoardResponse.from(board);
                    redisSet(id, boardResponse);
                    return boardResponse;
                });
    }

    private BoardResponse redisGet(Long id) {
        return redisManager.get(RedisKeyPrefix.BOARD_DETAIL, id, BoardResponse.class);
    }

    private void redisSet(Long id, BoardResponse boardResponse) {
        redisManager.set(RedisKeyPrefix.BOARD_DETAIL, id, boardResponse);
    }

    @Transactional
    public Board save(BoardRequest boardRequest) {
        String userId = SecurityUtill.getUserId();
        Member member = memberService.getMemberEntityByUserId(userId);
        return boardRepository.save(Board.from(boardRequest, member));
    }

    @Transactional
    public void update(BoardRequest boardRequest) {
        Board board = boardRepository.findById(boardRequest.getBoardId()).orElseThrow(IllegalArgumentException::new);
        Board updateBoard = board.updateForm(boardRequest);
        redisSet(boardRequest.getBoardId(), BoardResponse.from(updateBoard));
    }
}
