package com.hsyuan.tliaswebstduy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsyuan.tliaswebstduy.mapper.EmpMapper;
import com.hsyuan.tliaswebstduy.mapper.OperateLogMapper;
import com.hsyuan.tliaswebstduy.mapper.StudentMapper;
import com.hsyuan.tliaswebstduy.pojo.*;
import com.hsyuan.tliaswebstduy.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private OperateLogMapper operateLogMapper;
    @Override
    public JobOption getEmpJobData() {
        List<Map<String,Object>> jobData = empMapper.countEmpJobData();
//        JobOption jobOption = new JobOption();
//        for(Map<String,Object> item:jobData){
//            String pos = (String) item.get("pos");
//            Long num = (Long) item.get("num");
//            jobOption.getJobList().add(pos);
//            jobOption.getDataList().add(num);
//        }
        List<Object> jobList = jobData.stream().map(item -> item.get("pos")).toList();
        List<Object> dataList = jobData.stream().map(item -> item.get("num")).toList();
        return new JobOption(jobList,dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return studentMapper.countStudentDegreeData();
    }

    @Override
    public ClazzOption getStudentCountData() {
        List<Map<String,Object>> clazzData = studentMapper.countStudentClazzData();
        List<Object> nameList = clazzData.stream().map(item -> item.get("name")).toList();
        List<Object> dataList = clazzData.stream().map(item -> item.get("num")).toList();
        return new ClazzOption(nameList,dataList);
    }

    @Override
    public PageResult<OperateLog> getLogs(LogQueryParam logQueryParam) {
        // 分页查询操作日志
        PageHelper.startPage(logQueryParam.getPage(), logQueryParam.getPageSize());
        List<OperateLog> operateLogs = operateLogMapper.selectList();
        Page<OperateLog> page = (Page<OperateLog>) operateLogs;
        return new PageResult<>(page.getTotal(),page.getResult());
    }
}
