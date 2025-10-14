package com.web.site.global.config.security;


import com.web.site.global.common.filter.JwtAuthFilter;
import com.web.site.global.common.filter.LoginFilter;
import com.web.site.global.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    //권한 예외 처리
    private final CustomAccessDeniedHandler accessDeniedHandler;
    //토큰 없이 접근 예외 처리
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    private final AuthenticationConfiguration authenticationConfiguration;

    private static final String[] AUTH_ALLOWLIST = {
            "/auth/login","/api/v1/members" , "/view/**", "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
            "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html", "/css/**", "/js/**" ,
            "/index", "/", "/actuator/**", "/coupon/v1/**" , "/favicon.ico"
    };

    /*   @Bean
       public BCryptPasswordEncoder passwordEncoder() {
           return new BCryptPasswordEncoder();
       }
   */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Sha512PasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*여기가 일반적인 restApi 설정, 추후 필요 로직 알아서 추가*/
        http.csrf(s -> s.disable())
                .cors(s -> s.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(f -> f.disable());

        //이거 넣으면 모든 url에 토큰 검사함
        http.authorizeHttpRequests(a -> a
                .requestMatchers(AUTH_ALLOWLIST).permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/employees/**").hasRole("USER")
                .requestMatchers("/departments/**").hasRole("USER")
                .anyRequest().authenticated()
        );

        //먼저 토큰 검사 및 권한 부여
        http.addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        //특정한 URL일때만 필터 실행
        http.addFilterAt(new LoginFilter(jwtUtil, authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);
        //토큰 없이 접근 예외 처리, 권한 에러시 처리
        http.exceptionHandling(e ->
                e.authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
        );
        return http.build();
    }
}