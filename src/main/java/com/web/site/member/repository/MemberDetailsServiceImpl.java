
package com.web.site.member.repository;

import com.web.site.member.domain.entity.CustomUserDetails;
import com.web.site.member.domain.entity.Member;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException(userId));
        return Objects.nonNull(member) ? new CustomUserDetails(member.getUserId(), member.getPassword(), member.getRole()) : null;
    }
}

