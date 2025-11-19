package com.hsyuan.tliaswebstduy.controller;

import com.hsyuan.tliaswebstduy.anno.LogOperation;
import com.hsyuan.tliaswebstduy.pojo.Clazz;
import com.hsyuan.tliaswebstduy.pojo.ClazzQueryParam;
import com.hsyuan.tliaswebstduy.pojo.PageResult;
import com.hsyuan.tliaswebstduy.pojo.Result;
import com.hsyuan.tliaswebstduy.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clazzs")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    // 查询班级列表
    @GetMapping
    public Result getClazz(ClazzQueryParam param){
        log.info("getClazz");
        PageResult<Clazz> clazzList = clazzService.getClazz(param);
        return Result.success(clazzList);
    }
    @GetMapping("/list")
    public Result getClazzList(ClazzQueryParam param){
        log.info("getClazzList");
        List<Clazz> clazzList = clazzService.getClazzList(param);
        return Result.success(clazzList);
    }
    @LogOperation
    @PostMapping
    public Result addClazz(@RequestBody Clazz clazz){
        log.info("addClazz:{}",clazz);
        clazzService.addClazz(clazz);
        return Result.success();
    }
    @LogOperation
    @DeleteMapping("/{id}")
    public Result deleteClazz(@PathVariable Integer id){
        log.info("deleteClazz:{}",id);
        clazzService.deleteClazz(id);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result getClazzById(@PathVariable Integer id){
        log.info("getClazzById:{}",id);
        Clazz clazz = clazzService.getClazzById(id);
        return Result.success(clazz);
    }
    @LogOperation
    @PutMapping()
    public Result updateClazz(@RequestBody Clazz clazz){
        log.info("updateClazz:{}",clazz);
        clazzService.updateClazz(clazz);
        return Result.success();
    }
}
