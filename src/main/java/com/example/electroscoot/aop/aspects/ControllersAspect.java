package com.example.electroscoot.aop.aspects;

import org.aspectj.lang.JoinPoint;

public interface ControllersAspect {
    void logAllMethodsAdvice(JoinPoint jp);
}
