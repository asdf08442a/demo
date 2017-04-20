package com.demo.web.aop;

import com.demo.api.dto.response.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * 采用AOP的方式处理参数问题。
 */
@Component
@Aspect
public class ControllerAop {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAop.class);

    @Pointcut("execution(* com.demo.web.controller.*.*(..))")
    public void aopMethod() {
    }

    @Around("aopMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("before method invoking!");
        BindingResult bindingResult = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BindingResult) {
                bindingResult = (BindingResult) arg;
            }
        }
        if (bindingResult != null && bindingResult.hasErrors()) {
            StringBuilder errorInfo = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorInfo.append("[" + fieldError.getField() + ":" + fieldError.getDefaultMessage() + "]");
            }
            logger.error("error msg:{}", errorInfo.toString());
            return new Response().failure(errorInfo.toString());
        }
        return joinPoint.proceed();
    }

    @Around("aopMethod()")
    public Object getElapseTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Long || arg instanceof BindingResult) {
                continue;
            }
            logger.info("{} 请求参数：{}", joinPoint.getTarget().getClass() + "." + joinPoint.getSignature().getName(), arg);
        }
        Object o = joinPoint.proceed();
        logger.info("{} 返回结果：{} 耗时:{}ms", joinPoint.getTarget().getClass() + "." + joinPoint.getSignature().getName(), o, System.currentTimeMillis() - begin);
        return o;
    }
}
