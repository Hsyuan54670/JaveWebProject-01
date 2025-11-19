package com.hsyuan.tliaswebstduy.service.impl;

import com.hsyuan.tliaswebstduy.mapper.EmpLogMapper;
import com.hsyuan.tliaswebstduy.pojo.EmpLog;
import com.hsyuan.tliaswebstduy.service.EmpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpLogServiceImpl implements EmpLogService {
    @Autowired
    private EmpLogMapper empLogMapper;
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void insert(EmpLog empLog) {
        empLogMapper.insert(empLog);
    }
}
