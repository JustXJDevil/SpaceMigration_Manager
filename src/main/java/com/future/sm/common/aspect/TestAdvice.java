package com.future.sm.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class TestAdvice {
    @Pointcut("bean(manMenuServiceImpl)")
    public void pointcut(){}

    @Before("pointcut()")
    public void before(){
        System.out.println("before...");
    }

    @After("pointcut()")
    public void after(){
        System.out.println("after...");
    }
    @AfterReturning("pointcut()")
    public void afterReturning(){
        System.out.println("afterReturning...");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing(){
        System.out.println("afterThrowing...");
    }
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pj) throws Throwable {
        System.out.println("before around...");
        Object obj = pj.proceed();
        System.out.println("after around...");
        return obj;
    }
}
