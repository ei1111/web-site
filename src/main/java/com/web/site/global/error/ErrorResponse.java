package com.web.site.global.error;

import com.web.site.global.common.util.CustomDateFormat;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Getter
@Builder
public class ErrorResponse {
    private String error;
    private String message;
    private String timestamp;

    public static ErrorResponse of(String errorCode, String errorMessage) {
        return ErrorResponse.builder()
                .error(errorCode)
                .message(errorMessage)
                .timestamp(CustomDateFormat.nowDateTime())
                .build();
    }

    public static ErrorResponse of(String errorCode, BindingResult bindingResult) {
        return ErrorResponse.builder()
                .error(errorCode)
                .message(bindingResult.getFieldError().getDefaultMessage())
                .timestamp(CustomDateFormat.nowDateTime())
                .build();
    }

    private static String createErrorMessage(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            if (!isFirst) {
                sb.append(", ");
            } else {
                isFirst = false;
            }

            sb.append("[");
            sb.append(fieldError.getField());
            sb.append("]");
            sb.append(fieldError.getDefaultMessage());
        }
        return sb.toString();
    }
}
