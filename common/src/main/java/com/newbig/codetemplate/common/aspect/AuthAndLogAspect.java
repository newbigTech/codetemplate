package com.newbig.codetemplate.common.aspect;

import com.newbig.codetemplate.common.annotation.NoAuth;
import com.newbig.codetemplate.common.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

import static com.newbig.codetemplate.common.constant.AppConstant.EXECUTION;

@Component
@Slf4j
@Aspect
public class AuthAndLogAspect {
    public static final ThreadLocal<String> userIdThread = new ThreadLocal<>();
    @Autowired(required = false)
    private HttpServletRequest request;

    @Pointcut(EXECUTION)
    public void methods() {
        // 拦截所有的 controller
    }


    @Before("methods()")
    public void doBefore(JoinPoint jp){
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        String userId = "";
        if (!targetMethod.isAnnotationPresent(NoAuth.class)) {
            userId = JwtUtil.getUserUuid(request);
            userIdThread.set(userId);
            log.info("url:{},userId:{},args:{}", request.getRequestURI(), userId, jp.getArgs());
        }
    }
}
