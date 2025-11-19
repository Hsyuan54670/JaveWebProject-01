package com.hsyuan.tliaswebstduy.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
/**
 * 与本项目无关
 * 用于学习Spring AOP,通过记录方法执行耗时
 */
@Slf4j
@Aspect  // 切面类AOP
@Component
public class RecordTimeAspect {
    /**
     * 记录方法执行耗时
     * @param pjp 连接点对象,用于获取方法签名、参数等信息
     * @return 方法执行结果
     * @throws Throwable 方法执行过程中可能抛出的异常
     */
    /**
     * 切入点表达式,用于指定哪些方法需要被拦截
     * execution(* com.hsyuan.tliaswebstduy.service.impl.*.*(..))
     * 解释:
     * *: 匹配任意返回类型
     * com.hsyuan.tliaswebstduy.service.impl.*: 匹配com.hsyuan.tliaswebstduy.service.impl包下的任意类
     * .*(..): 匹配任意方法名,任意参数
     */
    /**
     * 通知类型
     * around: 环绕通知,在方法调用前后都执行
     * before: 前置通知,在方法调用前执行
     * after: 后置通知,在方法调用后执行
     * afterReturning: 在方法正常返回后执行
     * afterThrowing: 在方法抛出异常后执行
     */
    //@Around("execution(* com.hsyuan.tliaswebstduy.service.impl.*.*(..))")         //优先使用

    //@Around("@annotation(com.hsyuan.tliaswebstduy.anno.LogOperation)")  // 方法上有LogOperation注解时生效
    public Object recordTime(ProceedingJoinPoint  pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        log.info("{} 执行耗时: {}ms", pjp.getSignature().getName(), end - start);
        return result;
    }
}
