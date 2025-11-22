package com.web.site.member.service;

import com.web.site.global.error.CustomException;
import com.web.site.global.error.ErrorCode;
import com.web.site.member.domain.dto.response.MemberResponse;
import com.web.site.member.domain.entity.Member;
import com.web.site.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponse getMemberResponseByUserId(String userId) {
        return getMemberEntityByUserId(userId).toResponse();
    }


    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    public Member getMemberEntityByUserId(String userId) {
        return memberRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
