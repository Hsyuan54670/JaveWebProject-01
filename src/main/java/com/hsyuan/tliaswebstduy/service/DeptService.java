package com.hsyuan.tliaswebstduy.service;


import com.hsyuan.tliaswebstduy.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> findAll();

    void deleteDeptById(Integer id);

    void addDept(Dept dept);

    Dept getDeptNameById(Integer id);

    void updateDept(Dept dept);
}
