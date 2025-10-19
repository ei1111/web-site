package com.web.site.member.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    @Comment("도시")
    private String city;

    @Comment("도로")
    private String street;

    @Comment("우편번호")
    private String zipcode;

    public static Address of(String city, String street, String zipcode) {
        return new Address(city, street, zipcode);
    }
}
