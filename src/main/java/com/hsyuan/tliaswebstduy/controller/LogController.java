package com.hsyuan.tliaswebstduy.controller;

import com.hsyuan.tliaswebstduy.pojo.LogQueryParam;
import com.hsyuan.tliaswebstduy.pojo.OperateLog;
import com.hsyuan.tliaswebstduy.pojo.PageResult;
import com.hsyuan.tliaswebstduy.pojo.Result;
import com.hsyuan.tliaswebstduy.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class LogController {
    @Autowired
    private ReportService reportService;
    @GetMapping("/log/page")
    public Result getOperateLog(LogQueryParam logQueryParam) {
        log.info("获取操作日志");
        PageResult<OperateLog> operateLogs = reportService.getLogs(logQueryParam);
        return Result.success(operateLogs);
    }
}
