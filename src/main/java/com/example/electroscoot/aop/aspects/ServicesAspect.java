package com.example.electroscoot.aop.aspects;

import org.aspectj.lang.JoinPoint;

public interface ServicesAspect {
    void logAllMethodsAdvice(JoinPoint jp);
}
