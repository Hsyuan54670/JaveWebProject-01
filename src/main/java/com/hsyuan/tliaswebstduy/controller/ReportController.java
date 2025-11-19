package com.hsyuan.tliaswebstduy.controller;

import com.hsyuan.tliaswebstduy.pojo.JobOption;
import com.hsyuan.tliaswebstduy.pojo.Result;
import com.hsyuan.tliaswebstduy.service.ReportService;
import com.hsyuan.tliaswebstduy.service.impl.ReportServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/empJobData")
    public Result getEmpJobData() {
        log.info("统计员工岗位数据");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }
    @GetMapping("/empGenderData")
    public Result getEmpGenderData() {
        log.info("统计员工性别数据");
        return Result.success(reportService.getEmpGenderData());
    }
    @GetMapping("/studentDegreeData")
    public Result getStudentDegreeData() {
        log.info("统计学生学历数据");
        return Result.success(reportService.getStudentDegreeData());
    }
    @GetMapping("/studentCountData")
    public Result getStudentCountData() {
        log.info("统计学生人数数据");
        return Result.success(reportService.getStudentCountData());
    }

}
