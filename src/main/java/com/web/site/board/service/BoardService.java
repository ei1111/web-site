package com.web.site.board.service;


import com.web.site.board.domain.entity.Board;
import com.web.site.board.domain.dto.response.BoardPageResponse;
import com.web.site.board.domain.dto.request.BoardRequest;
import com.web.site.board.domain.dto.response.BoardResponse;
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
                    Board board = getBoardEntity(id);
                    BoardResponse boardResponse = board.toResponse();
                    redisSet(id, boardResponse);
                    return boardResponse;
                });
    }

    private Board getBoardEntity(Long id) {
        return boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);
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
        return boardRepository.save(boardRequest.from(member));
    }

    @Transactional
    public void update(BoardRequest boardRequest) {
        Board board = getBoardEntity(boardRequest.getBoardId());
        board.update(boardRequest);
        redisSet(boardRequest.getBoardId(), board.toResponse());
    }
}
