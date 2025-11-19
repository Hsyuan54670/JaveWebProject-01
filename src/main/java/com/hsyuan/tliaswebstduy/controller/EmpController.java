package com.hsyuan.tliaswebstduy.controller;

import com.hsyuan.tliaswebstduy.anno.LogOperation;
import com.hsyuan.tliaswebstduy.pojo.Emp;
import com.hsyuan.tliaswebstduy.pojo.EmpQueryParam;
import com.hsyuan.tliaswebstduy.pojo.Result;
import com.hsyuan.tliaswebstduy.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;
//    @GetMapping()
//    public Result findByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
//         log.info("分页查询：{} ，{}",page,pageSize);
//        return Result.success(empService.findByPage(page,pageSize));
//    }
//    @GetMapping
//    public Result findByPageHelper(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize){
//        log.info("分页查询：{} ，{}",page,pageSize);
//        return Result.success(empService.getEmpByPageHelper(page,pageSize));
//    }
//    @GetMapping
//    public Result findByPick(@RequestParam(required = false) String name,
//                             @RequestParam(required = false) Integer gender,
//                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
//                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
//                             @RequestParam(defaultValue = "1") Integer page,
//                             @RequestParam(defaultValue = "10") Integer pageSize){
//        log.info("查询：{} ，{} ，{} ，{} ，{} ，{}",name,gender,begin,end,page,pageSize);
//        return Result.success(empService.findByPick(name,gender,begin,end,page,pageSize));
//    }

    @GetMapping
    public Result findByPick(EmpQueryParam empQueryParam){
        log.info("查询：{} ",empQueryParam);
        return Result.success(empService.findByPick(empQueryParam));
    }
    @LogOperation
    @PostMapping
    public Result insertEmp(@RequestBody Emp emp){
        log.info("新增：{} ",emp);
        empService.insertEmp(emp);
        return Result.success();
    }

    @LogOperation
    @DeleteMapping
    public Result deleteEmp(@RequestParam List<Integer> ids){
        log.info("删除：{} ",ids);
        empService.deleteEmps(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findEmpByIds(@PathVariable Integer id){
        log.info("查询：{} ",id);
        return Result.success(empService.findEmpById(id));
    }
    @LogOperation
    @PutMapping
    public Result updateEmp(@RequestBody Emp emp){
        log.info("更新：{} ",emp);
        empService.updateEmp(emp);
        return Result.success();
    }
    @GetMapping("/list")
    public Result findAllJob1(){
        return Result.success(empService.findAllJob1());
    }
    @PutMapping("/changePassword")
    public Result changePassword(@RequestBody Emp emp){
        log.info("更新密码：{} ",emp);
        empService.changePassword(emp);
        return Result.success();
    }
}