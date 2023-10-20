package com.example.electroscoot.aop.pointcuts;

import org.aspectj.lang.annotation.Pointcut;

public class ServicesPointcut {
    @Pointcut("within(com.example.electroscoot.services.*)")
    public void allMethods() {}
}
