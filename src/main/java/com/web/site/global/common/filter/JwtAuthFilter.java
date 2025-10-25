package com.web.site.global.common.filter;


import com.web.site.global.common.util.JwtUtil;
import com.web.site.member.domain.entity.CustomUserDetails;
import com.web.site.member.domain.entity.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

//요청에 한번만 실행
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            //필터 종료
            return;
        }

        String token = authorization.split(" ")[1];

        //토큰 소멸시간 검증 true 소멸, false 소멸 되지 않음
        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            //필터 종료
            return;
        }

        String userId = jwtUtil.getUserId(token);
        String role = jwtUtil.getRole(token);

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .userId(userId)
                .role(Role.valueOf(role))
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}