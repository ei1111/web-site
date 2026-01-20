package com.web.site.member.service;

import com.web.site.global.common.config.security.Sha512PasswordEncoder;
import com.web.site.global.error.BusinessException;
import com.web.site.global.error.ErrorCode;
import com.web.site.member.domain.dto.reqeust.MemberModifyRequest;
import com.web.site.member.domain.dto.reqeust.MemberRequest;
import com.web.site.member.domain.dto.response.MemberResponse;
import com.web.site.member.domain.entity.Member;
import com.web.site.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class MemberServiceTx {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse save(MemberRequest memberRequest) {
        validateUserIdAndEmail(memberRequest);
        String password = passwordEncoder.encode(memberRequest.getPassword());
        return memberRepository.save(memberRequest.toMemberEntity(password)).toResponse();
    }

    private void validateUserIdAndEmail(MemberRequest request) {
        String userId = request.getUserId();

        if (memberRepository.existsByUserId(userId)) {
            throw new BusinessException(ErrorCode.EXIST_USER_ID);
        }

        String email = request.getEmail();
        if (memberRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.EXIST_EMAIL);
        }
    }

    @Transactional
    public void update(MemberModifyRequest request, String userId) {
        Member member = getMemberEntityByUserId(userId);
        String password = encodePasswordIfPresent(request);
        member.update(request, password);
    }

    private String encodePasswordIfPresent(MemberModifyRequest request) {
        String password = request.getPassword();

        if (StringUtils.hasText(password)) {
            password =  passwordEncoder.encode(password);
        }

        return password;
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }


    public Member getMemberEntityByUserId(String userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
