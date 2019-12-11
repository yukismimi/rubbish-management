package com.yukismimi.rubbishmanagement.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Before("execution( * com.yukismimi..*Controller.*(..))")
    public void beforeLog(JoinPoint joinPoint) {
        String clazz = joinPoint.getTarget().getClass().getName();
        String method = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("{} - {} is called by params {}.", clazz, method, args);
    }

    @Before("execution( * com.yukismimi.rubbishmanagement.api.Api.call(String, int ,int))")
    public void beforeCallApi(JoinPoint joinPoint){
        log.info("Api is called by params {}.", joinPoint.getArgs());
    }
}
