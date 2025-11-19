package com.hsyuan.tliaswebstduy.exception;

import com.hsyuan.tliaswebstduy.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

// 全局异常处理类
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result handleException(Exception e){
        log.error("全局异常捕获：{}",e.getMessage());
        return Result.error("出错啦，请联系管理员~");
    }
    @ExceptionHandler  // 处理数据库重复键异常
    public Result handleDuplicateKeyException(DuplicateKeyException e){
        log.error("全局异常捕获：{}",e.getMessage());
        // 从异常信息中提取手机号
        String msg = e.getMessage();
        //Duplicate entry '18360796168' for key 'emp.phone'
        // 提取手机号
        String phone = msg.substring(msg.indexOf("'")+1, msg.indexOf("'")+12);
        return Result.error("'"+phone+"'已存在!");
    }
}
