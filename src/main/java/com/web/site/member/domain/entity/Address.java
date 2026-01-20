package com.web.site.member.domain.entity;

import com.web.site.member.domain.dto.reqeust.MemberModifyRequest;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.StringUtils;


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

    public void updateAddress(MemberModifyRequest request) {
        String city = request.getCity();

        if (StringUtils.hasText(city)) {
            this.city = city;
        }

        String street = request.getStreet();


        if (StringUtils.hasText(street)) {
            this.street = street;
        }

        String zipcode = request.getZipcode();

        if (StringUtils.hasText(zipcode)) {
            this.zipcode = zipcode;
        }

    }
}
