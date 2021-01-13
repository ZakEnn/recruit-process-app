package com.zakenn.recruit.service;

import lombok.extern.apachecommons.CommonsLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@CommonsLog
public class ProcessMonitor {

     @Before("execution(* org.activiti.api.process.*.*(..))")
     public void logBefore(JoinPoint joinPoint) {
         log.info("***** START  **** " + joinPoint.getSignature().getDeclaringType().getSimpleName());
     }


     @After("execution(* org.activiti.api.process.*.*(..))")
     public void logAfter(JoinPoint joinPoint) {
         log.info("***** END  **** " + joinPoint.getSignature().getDeclaringType().getSimpleName());
     }

    }

