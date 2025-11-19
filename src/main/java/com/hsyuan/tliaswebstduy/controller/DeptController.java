package com.hsyuan.tliaswebstduy.controller;

import com.hsyuan.tliaswebstduy.anno.LogOperation;
import com.hsyuan.tliaswebstduy.pojo.Dept;
import com.hsyuan.tliaswebstduy.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hsyuan.tliaswebstduy.pojo.Result;
import java.util.List;
@Slf4j
@RequestMapping("/depts")  //统一设置请求路径的前缀。请求路径等于前缀加上@GetMapping("/depts")中的路径
@RestController
public class DeptController {

    //private static final Logger log = LoggerFactory.getLogger(DeptController.class);   //加注解
    @Autowired
    private DeptService deptService;

    //@RequestMapping(value = "/depts",method = RequestMethod.GET)
    //@GetMapping("/depts")
    @GetMapping
    public Result getDepts() {
        //System.out.println("查询全部部门数据");
        log.info("查询全部部门数据");
        List<Dept> deptList=deptService.findAll();
        return Result.success(deptList);
    }

//    @DeleteMapping("/depts/{id}")   //http://localhost:8080/depts/1
//    public Result deleteDeptById(@PathVariable String id) {
//        System.out.println("根据id删除部门数据");
//        deptService.deleteDeptById(id);
//        return Result.success();
//    }


    //@DeleteMapping("/depts")
    @LogOperation
    @DeleteMapping
    public Result deldeptById(@RequestParam(value = "id",required = false) Integer id) {
        //System.out.println("根据id删除部门数据");
        log.info("根据id删除部门数据: {}",id);
        deptService.deleteDeptById(id);
        return Result.success();
    }

    //@PostMapping("/depts")
    @LogOperation
    @PostMapping
    public Result addDept(@RequestBody Dept dept) {           //json请求体
        //@RequestBody 要求前端请求体中携带json格式的数据
        //springmvc将请求体中的json数据转为java对象
        //要求pojo类中的属性名和json数据中的key保持一致


        //System.out.println("添加部门数据");
        log.info("添加部门数据:{}",dept);
        deptService.addDept(dept);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getDeptById(@PathVariable(value = "id") Integer id) {
        //System.out.println("根据id查询部门数据");
        log.info("根据id查询部门数据: {}",id);
        Dept dept = deptService.getDeptNameById(id);
        return Result.success(dept);
    }

    //@PutMapping("/depts")
    @LogOperation
    @PutMapping
    public Result updateDept(@RequestBody Dept dept) {
        //System.out.println("根据id修改部门数据");
        log.info("根据id修改部门数据: {}",dept);
        deptService.updateDept(dept);
        return Result.success();
    }
}
