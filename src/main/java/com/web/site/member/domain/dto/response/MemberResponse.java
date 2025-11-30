package com.web.site.member.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.site.global.enums.Role;
import com.web.site.member.domain.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "회원 가입 response")
public class MemberResponse {
    @Schema(description = "순번" , example = "1")
    private int count;

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

    @Schema(description = "회원권한", example = "U")
    private Role role;

    public static MemberResponse from(Member member,  int counter) {
        return new MemberResponse(
                counter,
                member.getUserId(),
                member.getName(),
                member.getEmail(),
                member.getCity(),
                member.getStreet(),
                member.getZipCode(),
                member.getRole()
        );
    }
}
