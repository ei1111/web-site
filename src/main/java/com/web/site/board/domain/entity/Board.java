package com.web.site.board.domain.entity;

import com.web.site.board.domain.dto.BoardRequest;
import com.web.site.board.domain.dto.BoardResponse;
import com.web.site.global.audit.BaseEntity;
import com.web.site.member.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.StringUtils;

@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("제목")
    private String title;

    @Lob
    @Comment("내용")
    private String content;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    @Builder
    public Board(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void update(BoardRequest boardRequest) {
        String reqtitle = boardRequest.getTitle();
        String reqContent = boardRequest.getContent();

        if (StringUtils.hasText(reqtitle)) {
            this.title = reqtitle;
        }

        if (StringUtils.hasText(reqContent)) {
            this.content = reqContent;
        }
    }

    public BoardResponse toResponse() {
        return BoardResponse.builder()
                .boardId(this.id)
                .title(this.title)
                .content(this.content)
                .build();
    }
}
