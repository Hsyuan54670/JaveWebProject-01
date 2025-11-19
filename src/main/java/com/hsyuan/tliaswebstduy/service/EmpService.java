package com.hsyuan.tliaswebstduy.service;

import com.hsyuan.tliaswebstduy.pojo.EmpQueryParam;
import com.hsyuan.tliaswebstduy.pojo.LoginInfo;
import com.hsyuan.tliaswebstduy.pojo.PageResult;
import com.hsyuan.tliaswebstduy.pojo.Emp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EmpService {
    //PageResult<Emp> findByPage(Integer pageNum,Integer pageSize);
    //PageResult<Emp> getEmpByPageHelper(Integer page, Integer pageSize);

    //PageResult findByPick(String name, Integer gender, LocalDate begin, LocalDate end, Integer page, Integer pageSize);
    PageResult<Emp> findByPick(EmpQueryParam empQueryParam);

    void insertEmp(Emp emp);

    void deleteEmps(List<Integer> ids);

    void updateEmp(Emp emp);

    Emp findEmpById(Integer id);

    List<Emp> findAllJob1();

    LoginInfo LoginInfo(Emp emp);

    void changePassword(Emp emp);
}