package com.web.site.member.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "회원 수정 request")
public class MemberModifyRequest {

    @Schema(description = "비밀번호", example = "1")
    private String password;

    @Schema(description = "이름", example = "김길동")
    private String name;

    @Schema(description = "이메일", example = "ccc@naver.com")
    private String email;

    @Schema(description = "거주도시", example = "서울")
    private  String city;

    @Schema(description = "도로명", example = "양갈로")
    private String street;

    @Schema(description = "우편번호", example = "01321")
    private String zipcode;

}
