package com.web.site.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.site.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "회원 가입 response")
public class MemberResponse {
    @Schema(description = "아이디" , example = "ADMIN")
    private String userId;

    @Schema(description = "이름" , example = "홍길동")
    private String name;

    @Schema(description = "이메일", example = "abc@naver.com")
    private String email;

    @Schema(description = "거주도시", example = "서울")
    private String city;

    @Schema(description = "도로명", example = "양갈로")
    private String street;

    @Schema(description = "우편번호", example = "01321")
    private String zipcode;

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getUserId(),
                member.getName(),
                member.getEmail(),
                member.getCity(),
                member.getStreet(),
                member.getZipCode()
        );
    }
}
