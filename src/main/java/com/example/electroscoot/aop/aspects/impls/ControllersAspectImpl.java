package com.example.electroscoot.aop.aspects.impls;

import com.example.electroscoot.aop.aspects.ControllersAspect;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Aspect
public class ControllersAspectImpl implements ControllersAspect {
    private final Logger logger;

    @Override
    @Before("com.example.electroscoot.aop.pointcuts.ControllersPointcut.allMethods()")
    public void logAllMethodsAdvice(JoinPoint jp) {
        String className = jp.getSignature().getDeclaringTypeName();
        if (className.contains("AuthenticationController")) {
            logger.info("The <{}> method is called from {}",
                    jp.getSignature().getName(),
                    jp.getSignature().getDeclaringTypeName().split("controllers.")[1]);
        } else {
            logger.info("The <{}> method is called from {} with args: {}",
                    jp.getSignature().getName(),
                    jp.getSignature().getDeclaringTypeName().split("controllers.")[1],
                    jp.getArgs());
        }
    }
}
