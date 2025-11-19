package com.hsyuan.tliaswebstduy.aop;

import com.hsyuan.tliaswebstduy.anno.LogOperation;
import com.hsyuan.tliaswebstduy.mapper.OperateLogMapper;
import com.hsyuan.tliaswebstduy.pojo.OperateLog;
import com.hsyuan.tliaswebstduy.utils.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class OperationLogAspect {
    @Autowired
    private OperateLogMapper operateLogMapper;


    @Around("@annotation(com.hsyuan.tliaswebstduy.anno.LogOperation)")
    public Object logOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        long costTime = end - start;
        OperateLog logOperate = new OperateLog();
        logOperate.setOperateEmpId(getCurrentEmpId());
        logOperate.setOperateTime(LocalDateTime.now());
        logOperate.setClassName(joinPoint.getTarget().getClass().getName());
        logOperate.setMethodName(joinPoint.getSignature().getName());
        logOperate.setMethodParams(Arrays.toString(joinPoint.getArgs()));
        logOperate.setReturnValue(result!=null? result.toString() : "void");
        logOperate.setCostTime(costTime);
        log.info("logOperate: {}", logOperate);
        operateLogMapper.insert(logOperate);
        return result;
    }


    private Integer getCurrentEmpId() {
        Integer empId= (Integer) ThreadLocalUtils.get();
        log.info("员工ID:{}",empId);
        return empId;
    }
}
