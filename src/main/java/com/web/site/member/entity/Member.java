package com.web.site.member.entity;


import com.web.site.global.audit.BaseTimeEntity;
import com.web.site.member.form.MemberRequest;
import com.web.site.member.form.MemberResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public void update(MemberRequest request, PasswordEncoder passwordEncoder) {
        if (StringUtils.hasText(request.getPassword())) {
            this.password = passwordEncoder.encode(request.getPassword());
        }

        if(!Objects.equals(request.getName(), this.name)) {
            this.name = request.getName();
        }

        if(!Objects.equals(request.getEmail(), this.email)) {
            this.email = request.getEmail();
        }
    }

    public MemberResponse toResponse() {
        return MemberResponse.builder()
                .id(this.id)
                .userId(this.userId)
                .password(this.password)
                .name(this.name)
                .email(this.getEmail())
                .city(this.getCity())
                .street(this.getStreet())
                .zipcode(this.getZipCode())
                .build();
    }



}
