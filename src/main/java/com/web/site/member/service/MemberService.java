package com.web.site.member.service;

import com.web.site.global.error.CustomException;
import com.web.site.global.error.ErrorCode;
import com.web.site.member.entity.Member;
import com.web.site.member.form.MemberRequest;
import com.web.site.member.form.MemberResponse;
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
        Member member = memberRepository.save(memberRequest.toMemberEntity(password));
        return member.toResponse();
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

    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    public MemberResponse findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return MemberResponse.from(member);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public void update(MemberRequest request) {
        Member member = findByUserId(request.getUserId());
        member.update(request, passwordEncoder);
    }

    public Member findByUserId(String userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
