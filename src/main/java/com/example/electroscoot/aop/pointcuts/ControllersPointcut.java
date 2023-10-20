package com.example.electroscoot.aop.pointcuts;

import org.aspectj.lang.annotation.Pointcut;

public class ControllersPointcut {
    @Pointcut("within(com.example.electroscoot.controllers.*)")
    public void allMethods() {}
}
