package com.web.site.member.form;

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
@NoArgsConstructor
@Schema(description = "로그인 request")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {

    private String userId;
    private String password;
}
