package com.web.site.member.form;

import com.web.site.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "회원 가입 response")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {
    @Schema(description = "회원번호")
    private Long id;

    @Schema(description = "아이디")
    private String userId;

    @Schema(description = "비밀번호")
    private String password;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "거주도시", example = "서울")
    String city;

    @Schema(description = "도로명", example = "양갈로")
    String street;

    @Schema(description = "우편번호", example = "01321")
    String zipcode;

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getUserId(),
                member.getPassword(),
                member.getName(),
                member.getEmail(),
                member.getCity(),
                member.getStreet(),
                member.getZipCode()
        );
    }
}
