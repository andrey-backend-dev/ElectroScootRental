package com.example.electroscoot.aop.aspects.impls;

import com.example.electroscoot.aop.aspects.ServicesAspect;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Aspect
public class ServicesAspectImpl implements ServicesAspect {
    private final Logger logger;

    @Override
    @Before("com.example.electroscoot.aop.pointcuts.ServicesPointcut.allMethods()")
    public void logAllMethodsAdvice(JoinPoint jp) {
        String className = jp.getSignature().getDeclaringTypeName();
        if (className.contains("AuthenticationService") ||
                (className.contains("UserService") && jp.getSignature().getName().contains("create"))) {
            logger.info("The <{}> method is called from {}",
                    jp.getSignature().getName(),
                    jp.getSignature().getDeclaringTypeName().split("services.")[1]);
        } else if (className.contains("JwtService")) {
            logger.debug("The <{}> method is called from {}",
                    jp.getSignature().getName(),
                    jp.getSignature().getDeclaringTypeName().split("services.")[1]);
        } else {
            logger.info("The <{}> method is called from {} with args: {}",
                    jp.getSignature().getName(),
                    jp.getSignature().getDeclaringTypeName().split("services.")[1],
                    jp.getArgs());
        }
    }
}
