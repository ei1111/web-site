package com.web.site.member.controller.api;

import com.web.site.member.domain.dto.MemberModifyRequest;
import com.web.site.member.domain.entity.CustomUserDetails;
import com.web.site.member.domain.dto.MemberRequest;
import com.web.site.member.domain.dto.MemberResponse;
import com.web.site.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "1. 회원가입 CRUD API")
@RequestMapping("/api/v1")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/members")
    @Operation(summary = "회원 정보 등록 API")
    public ResponseEntity<String> save(@Valid @RequestBody MemberRequest request) {
        memberService.save(request);
        return ResponseEntity.ok("회원 가입 성공!");
    }

    @GetMapping("/members/me")
    @Operation(summary = "회원 아이디로 상세 조회 API")
    public ResponseEntity<MemberResponse> member(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(memberService.getMemberResponseByUserId(userDetails.getUsername()));
    }

    @PutMapping("/members")
    @Operation(summary = "회원 정보 수정 API")
    public ResponseEntity<String> update( @RequestBody MemberModifyRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        memberService.update(request, userDetails.getUsername());
        return ResponseEntity.ok().body("회원 수정 성공");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/members")
    @Operation(summary = "회원 리스트 API")
    public ResponseEntity<List<MemberResponse>> memberList() {
        return ResponseEntity.ok(memberService.findAll());
    }
}
