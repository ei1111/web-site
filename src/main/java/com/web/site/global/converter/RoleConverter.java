package com.web.site.global.converter;

import com.web.site.global.enums.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Optional;

//전역에서 자동으로 변경되는 걸로 사용하고 싶으면 (autoApply = true) 설정
@Converter(autoApply = false)
public class RoleConverter implements AttributeConverter<Role, String>  {

    @Override
    public String convertToDatabaseColumn(Role role) {
        return Optional.ofNullable(role)
                .map(Role::getCode)
                .orElse(null);
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
                .map(Role::fromCode)
                .orElse(null);
    }
}
