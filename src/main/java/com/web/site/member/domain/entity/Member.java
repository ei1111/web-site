package com.web.site.member.domain.entity;


import com.web.site.global.audit.BaseTimeEntity;
import com.web.site.member.domain.dto.MemberModifyRequest;
import com.web.site.member.domain.dto.MemberResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @Comment("회원번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Comment("아이디")
    @Column(unique = true)
    private String userId;

    @Comment("비밀번호")
    private String password;

    @Comment("이름")
    private String name;

    @Comment("이메일")
    @Column(unique = true)
    private String email;

    @Comment("권한")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;

    public String getCity() {
        return address.getCity();
    }

    public String getStreet() {
        return address.getStreet();
    }

    public String getZipCode() {
        return address.getZipcode();
    }

    private Member(String userId, String password, String name, String email, Address address) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = makeRole(userId);
        this.address = address;
    }

    public static Member from(String userId, String password, String name, String email,Address address) {
        return new Member(userId, password, name, email, address);
    }

    private Role makeRole(String userId) {
        return userId.equalsIgnoreCase("admin") ? Role.ADMIN: Role.USER;
    }

    public void update(MemberModifyRequest request, String password) {
            this.password = password;
            this.name = request.getName();
            this.email = request.getEmail();
    }

    public MemberResponse toResponse() {
        return MemberResponse.builder()
                .userId(this.userId)
                .name(this.name)
                .email(this.email)
                .city(this.getCity())
                .street(this.getStreet())
                .zipcode(this.getZipCode())
                .build();
    }
}
