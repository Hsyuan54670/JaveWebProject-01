package com.hsyuan.tliaswebstduy.service;



import com.hsyuan.tliaswebstduy.pojo.*;

import java.util.List;
import java.util.Map;

public interface ReportService {

    JobOption getEmpJobData();

    List<Map<String, Object>> getEmpGenderData();

    List<Map<String, Object>> getStudentDegreeData();

    ClazzOption getStudentCountData();

     PageResult<OperateLog> getLogs(LogQueryParam logQueryParam);
}
