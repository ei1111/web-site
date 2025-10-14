package com.web.site.member.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Comment("도시")
    private String city;

    @Comment("도로")
    private String street;

    @Comment("우편번호")
    private String zipcode;
}
