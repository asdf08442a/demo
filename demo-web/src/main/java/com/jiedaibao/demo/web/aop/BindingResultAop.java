package com.jiedaibao.demo.web.aop;

import com.jiedaibao.demo.api.dto.response.Response;
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
public class BindingResultAop {

    private static final Logger logger = LoggerFactory.getLogger(BindingResultAop.class);

    @Pointcut("execution(* com.jiedaibao.demo.web.controller.*.*(..))")
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
}
