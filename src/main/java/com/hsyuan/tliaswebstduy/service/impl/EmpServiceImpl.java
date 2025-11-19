package com.hsyuan.tliaswebstduy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hsyuan.tliaswebstduy.mapper.DeptMapper;
import com.hsyuan.tliaswebstduy.mapper.EmpExprMapper;
import com.hsyuan.tliaswebstduy.mapper.EmpLogMapper;
import com.hsyuan.tliaswebstduy.mapper.EmpMapper;
import com.hsyuan.tliaswebstduy.pojo.*;
import com.hsyuan.tliaswebstduy.service.EmpLogService;
import com.hsyuan.tliaswebstduy.service.EmpService;
import com.hsyuan.tliaswebstduy.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {


    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogService empLogService;
//    @Override
//    public PageResult<Emp> getEmpByPageHelper(Integer page, Integer pageSize) {
//        PageHelper.startPage(page, pageSize);
//        List<Emp> empList=empMapper.findByPageHelper();
//        Page<Emp> p= (Page<Emp>) empList;
//        return new PageResult<Emp>(p.getTotal(),empList);
//    }

//    @Override
//    public PageResult<Emp> findByPick(String name, Integer gender, LocalDate begin, LocalDate end, Integer page, Integer pageSize) {
//        PageHelper.startPage(page, pageSize);
//        List<Emp> empList=empMapper.findByPick(name,gender,begin,end);
//        Page<Emp> p= (Page<Emp>) empList;
//        return new PageResult<Emp>(p.getTotal(),empList);
//    }
    @Override
    public PageResult<Emp> findByPick(EmpQueryParam empQueryParam) {
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
        List<Emp> empList=empMapper.findByPick(empQueryParam);
        Page<Emp> p= (Page<Emp>) empList;
        return new PageResult<Emp>(p.getTotal(),empList);
    }
    /*
    exception:所有异常都回滚
    propagation: REQUIRED, // 传播行为 - REQUIRED:如果当前没有事务,就创建一个新事务,如果当前存在事务,就加入该事务,这是默认值
     propagation: REQUIRED_NEW, // 传播行为 - REQUIRED_NEW:如果当前没有事务,就创建一个新事务,如果当前存在事务,就开启一个新事务,并将当前事务挂起
     propagation: SUPPORTS, // 传播行为 - SUPPORTS:如果当前没有事务,就以非事务的方式执行,如果当前存在事务,就加入该事务
     propagation: NOT_SUPPORTED, // 传播行为 - NOT_SUPPORTED:如果当前没有事务,就以非事务的方式执行,如果当前存在事务,就将当前事务挂起
     propagation: MANDATORY, // 传播行为 - MANDATORY:如果当前没有事务,就抛出异常,如果当前存在事务,就加入该事务
     */
    @Transactional(rollbackFor = Exception.class)  // 开启事务管理 -默认出现运行时异常时回滚
    @Override
    public void insertEmp(Emp emp) {
        try {
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);

            //int i=1/0;

            List<EmpExpr> exprList = emp.getExprList();
            if (!CollectionUtils.isEmpty(exprList)) {
                // 遍历表达式列表,设置每个表达式的员工ID
                exprList.forEach(expr -> expr.setEmpId(emp.getId()));
                empExprMapper.insertBatch(exprList);
            }
        }finally {
            // 记录日志
            EmpLog empLog = new EmpLog();
            empLog.setOperateTime(LocalDateTime.now());
            empLog.setInfo("新增员工:" + emp.toString());
            empLogService.insert(empLog);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteEmps(List<Integer> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return;
        }
        empMapper.deleteBatch(ids);
        empExprMapper.deleteBatch(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateEmp(Emp emp) {
        empExprMapper.deleteBatch(Arrays.asList(emp.getId()));
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            // 遍历表达式列表,设置每个表达式的员工ID
            exprList.forEach(expr -> expr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }
    }

    @Override
    public Emp findEmpById(Integer id) {
        return empMapper.findEmpById(id);
    }

    @Override
    public List<Emp> findAllJob1() {

        return empMapper.findAllJob1();
    }

    @Override
    public LoginInfo LoginInfo(Emp emp) {
        Emp e = empMapper.selectByUserNameAndPassword(emp);
        if (e != null) {
            //生成令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("username", e.getUsername());
            String jwt = JwtUtils.generateToken(claims);
            log.info("登录成功,用户ID: {}", e.getId());
            return  new LoginInfo(e.getId(),e.getUsername(),e.getName() ,jwt);
        }
        return null;
    }

    @Override
    public void changePassword(Emp emp){
        empMapper.updatePassword(emp);
    }


//    @Override
//    public PageResult<Emp> findByPage(Integer pageNum, Integer pageSize) {
////        if(pageNum==null||pageSize==null){
////            pageNum=1;
////            pageSize=10;
////        }
//        int pageIndex=(pageNum-1)*pageSize;
//        List<Emp> empList=empMapper.findByPage(pageIndex, pageSize);
//        long total=empMapper.countEmp();
//        return new PageResult<>(total,empList);
//    }

}