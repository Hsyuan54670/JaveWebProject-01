package com.hsyuan.tliaswebstduy.service.impl;

import com.hsyuan.tliaswebstduy.anno.LogOperation;
import com.hsyuan.tliaswebstduy.mapper.DeptMapper;
import com.hsyuan.tliaswebstduy.pojo.Dept;
import com.hsyuan.tliaswebstduy.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    //@LogOperation
    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public void deleteDeptById(Integer id) {
        deptMapper.deleteDeptById(id);
    }

    @Override
    public void addDept(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.addDept(dept);
    }

    @Override
    public Dept getDeptNameById(Integer id) {
        return deptMapper.getDeptNameById(id);
    }

    @Override
    public void updateDept(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.updateDept(dept);
    }
}
