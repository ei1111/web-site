package com.web.site.member.domain.dto.reqeust;

import com.web.site.member.domain.entity.Address;
import com.web.site.member.domain.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "회원 가입 request")
public class MemberRequest {
    @NotBlank(message = "사용자 아이디는 필수 입니다,")
    @Schema(description = "아이디", example = "ADMIN")
    private String userId;

    @NotBlank(message = "사용자 비밀번호는 필수 입니다,")
    @Schema(description = "비밀번호", example = "1")
    private String password;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @NotBlank(message = "사용자 이메일는 필수 입니다,")
    @Schema(description = "이메일",example = "aaa@naver.com")
    private String email;

    @Schema(description = "거주도시", example = "서울")
    private  String city;

    @Schema(description = "도로명", example = "양갈로")
    private String street;

    @Schema(description = "우편번호", example = "01321")
    private String zipcode;


    public Member toMemberEntity(String password) {
        return Member.from(userId, password, name, email,  Address.of(city, street, zipcode));
    }
}
