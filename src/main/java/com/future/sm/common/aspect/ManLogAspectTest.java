package com.future.sm.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ManLogAspectTest {
    /*这个方法不用具体实现,主要是使用它的注解,方便一处修改全文修改*/
    //@Pointcut("bean(manUserServiceImpl)")
    @Pointcut("@annotation(com.future.sm.common.annotation.RequiredLog)")
    //@Pointcut("execution(* com.future.sm.manager.serviceImpl.ManUserServiceImpl.updateValidById(..))")
    //@Pointcut("within(com.future.sm.manager.serviceImpl.*ServiceImpl)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pj)throws Throwable{
        try{
            long end = 0l;
            System.out.println("target: "+pj.getTarget().toString());
            long start = System.currentTimeMillis();
            log.info("start: "+start);
            //调用下一个切面或目标方法
            Object obj = pj.proceed();
            end = System.currentTimeMillis();
            log.info("end: "+System.currentTimeMillis());
            log.info("end-start: "+(end-start));
            return obj;
        }catch (Throwable t){
            log.error(t.getMessage());
            throw t;
        }
    }
}
