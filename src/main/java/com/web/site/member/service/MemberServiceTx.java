package com.web.site.member.service;

import com.web.site.global.error.CustomException;
import com.web.site.global.error.ErrorCode;
import com.web.site.member.domain.dto.reqeust.MemberModifyRequest;
import com.web.site.member.domain.dto.reqeust.MemberRequest;
import com.web.site.member.domain.dto.response.MemberResponse;
import com.web.site.member.domain.entity.Member;
import com.web.site.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new CustomException(ErrorCode.EXIST_USER_ID);
        }

        String email = request.getEmail();
        if (memberRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.EXIST_EMAIL);
        }
    }

    @Transactional
    public void update(MemberModifyRequest request, String userId) {
        Member member = getMemberEntityByUserId(userId);
        String password = passwordEncoder.encode(member.getPassword());
        member.update(request, password);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }


    public Member getMemberEntityByUserId(String userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
