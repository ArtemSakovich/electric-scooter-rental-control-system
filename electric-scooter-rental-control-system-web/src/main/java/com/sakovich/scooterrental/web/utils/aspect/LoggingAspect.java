package com.sakovich.scooterrental.web.utils.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private long timeBeforeMethodInvocation;

    @Pointcut("execution(* (@com.sakovich.scooterrental.web.utils.annotation.Logging *).*(..))")
    public void loggingAnnotatedClassMethod() {
    }

    @Before("loggingAnnotatedClassMethod()")
    public void beforeLoggingMethods() {
        timeBeforeMethodInvocation = System.currentTimeMillis();
    }

    @AfterReturning(value = "loggingAnnotatedClassMethod()", returning = "returnValue")
    public void afterLoggingMethods(final JoinPoint joinPoint, Object returnValue) {
        long timeAfterMethodInvocation = System.currentTimeMillis();
        log.info("Method: " + joinPoint.getSignature().getDeclaringTypeName() + " has been called with argument(s): " +
                Arrays.toString(joinPoint.getArgs()) + "; Return value: " + returnValue + "; Working time: " +
                (timeAfterMethodInvocation - timeBeforeMethodInvocation) + " milliseconds");
    }
}
