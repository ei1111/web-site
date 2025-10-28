package com.web.site.global.audit.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * AOP 작성 클래스
 *
 * @since 1.0 2023-02-24
 */
@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class LogAspect {

    @Around("@annotation(logExecutionTime)")
    public Object workTime(ProceedingJoinPoint joinPoint, LogExecutionTime logExecutionTime) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        String methodName = joinPoint.getSignature().getName();
        String label = logExecutionTime.description().isEmpty() ? methodName : logExecutionTime.description();

        log.info("{} 실행 시간: {}ms", label, executionTime);


        return proceed;
    }
}
