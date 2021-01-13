package com.future.sm.common.aspect;

import com.future.sm.common.annotation.RequiredLog;
import com.future.sm.common.util.IPUtils;
import com.future.sm.common.util.ShiroUtils;
import com.future.sm.manager.pojo.ManLog;
import com.future.sm.manager.service.ManLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

@Component
@Aspect
public class ManLogAspect {
    @Autowired
    private ManLogService manLogService;

    @Pointcut("@annotation(com.future.sm.common.annotation.RequiredLog)")
    public void logPointcut(){}

    @Around("logPointcut()")
    public Object around(ProceedingJoinPoint pj) throws Throwable {
        long start = System.currentTimeMillis();
        Object obj = pj.proceed();
        long end = System.currentTimeMillis();
        saveObject(pj,end-start);
        return obj;
    }

    private void saveObject(ProceedingJoinPoint pj,long time) throws NoSuchMethodException {
        /**
         * 这里CGLIB和JDK的实现方式不同
         */
        ManLog manLog = getManLogForJDKProxy(pj, time);
        //3.持久化
            manLogService.insertObject(manLog);
    }

    /**
     * CGLIB的获取相关操作信息的方式
     *
     * @param pj
     * @param time
     * @return
     */
    private ManLog getManLogForCGLIB(ProceedingJoinPoint pj, long time) {
        //1.获取日志信息(先写下一步,明确这步需要哪些数据)
        //1.1获取方法签名,以方便获取方法的相关信息
        MethodSignature signature = (MethodSignature) pj.getSignature();
        //1.1.1获取该方法
        Method method = signature.getMethod();

        //1.2获取目标的方法名
        //1.2.1获取方法名
        /**
         * (不晓得为什么这个method JDK用会获取到很多null值)
         */
        String methodName = method.getName();
        //1.2.2获取到的是包名.类名
        /**
         * 注意这里JDK会获取service包而不是serviceImpl包
         */
        String packageAndClassName = signature.getDeclaringTypeName();
        //1.2.3拼接需要的包名.类名.方法名()
        String targetMethodName = packageAndClassName+"."+methodName+"()";

        //1.3获取方法上注解的value即操作名
        /**
         * 注意JDK Proxy在这里获取到的requiredLog是null
         */
        RequiredLog requiredLog = method.getAnnotation(RequiredLog.class);
        String operation = requiredLog.value();

        //1.4获取用户名,学完shiro再进行自定义实现,没有就先给自定名
        String username =
                ShiroUtils.getUser().getUsername();
        //1.5获取方法参数
        Object[] args = pj.getArgs();

        //2.封装日志信息
        ManLog manLog = new ManLog();
        manLog.setIp(IPUtils.getIpAddr());
        manLog.setUsername(username);
        manLog.setCreateTime(new Date());
        manLog.setMethod(targetMethodName);
        manLog.setOperation(requiredLog.value());
        manLog.setParams(Arrays.toString(args));
        manLog.setTime(time);
        return manLog;
    }

    /**
     * JDK Proxy获取相关日志信息的方式
     *
     * @param pj
     * @param time
     * @return
     * @throws NoSuchMethodException
     */
    private ManLog getManLogForJDKProxy(ProceedingJoinPoint pj, long time) throws NoSuchMethodException {
        //1.获取日志信息(先写下一步,明确这步需要哪些数据)
        //1.1获取该类字节码
        Class<?> targetClass = pj.getTarget().getClass();

        //1.2获取方法签名,以方便获取方法的相关信息
        MethodSignature signature = (MethodSignature) pj.getSignature();

        //1.3 获取该方法对象
        //1.3.1获取方法名
        String methodName = signature.getName();
        //1.3.2获取该方法
        Method targetMethod = targetClass.getDeclaredMethod(methodName,signature.getParameterTypes());

        //1.4获取目标的方法名
        //1.4.1获取到的是包名.类名
        String packageAndClassName = targetClass.getName();
        //1.4.2拼接需要的包名.类名.方法名()
        String targetMethodName = packageAndClassName+"."+methodName+"()";

        //1.4获取方法上注解的value即操作名
        RequiredLog requiredLog = targetMethod.getDeclaredAnnotation(RequiredLog.class);
        String operation = requiredLog.value();

        //1.5获取用户名,学完shiro再进行自定义实现,没有就先给自定名
        String username =
                ShiroUtils.getUser().getUsername();
        //1.6获取方法参数
        Object[] args = pj.getArgs();

        //2.封装日志信息
        ManLog manLog = new ManLog();
        manLog.setIp(IPUtils.getIpAddr());
        manLog.setUsername(username);
        manLog.setCreateTime(new Date());
        manLog.setMethod(targetMethodName);
        manLog.setOperation(operation);
        manLog.setParams(Arrays.toString(args));
        manLog.setTime(time);
        return manLog;
    }
}
