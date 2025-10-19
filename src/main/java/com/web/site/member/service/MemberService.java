package com.web.site.member.service;

import com.web.site.global.error.CustomException;
import com.web.site.global.error.ErrorCode;
import com.web.site.member.dto.MemberModifyRequest;
import com.web.site.member.entity.Member;
import com.web.site.member.dto.MemberRequest;
import com.web.site.member.dto.MemberResponse;
import com.web.site.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

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

    public MemberResponse getMemberResponseByUserId(String userId) {
        return getMemberEntityByUserId(userId).toResponse();
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

    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponse::from)
                .toList();
    }


    public Member getMemberEntityByUserId(String userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
