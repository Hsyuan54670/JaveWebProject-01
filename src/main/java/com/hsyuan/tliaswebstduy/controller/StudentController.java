package com.hsyuan.tliaswebstduy.controller;

import com.hsyuan.tliaswebstduy.anno.LogOperation;
import com.hsyuan.tliaswebstduy.pojo.*;
import com.hsyuan.tliaswebstduy.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    // 获取所有学生信息
    @GetMapping
    public Result getStudents(StudentQueryParam param) {
        log.info("getStudents,param:{}", param);
        PageResult<Student> pageResult = studentService.getStudents(param);
        return Result.success(pageResult);
    }

    //添加学生
    @LogOperation
    @PostMapping
    public Result addStudent(@RequestBody Student student) {
        log.info("addStudent,student:{}", student);
        studentService.addStudent(student);
        return Result.success();
    }

    //根据id获取学生信息
    @GetMapping("/{id}")
    public Result getStudentById(@PathVariable Integer id) {
        log.info("getStudentById,id:{}", id);
        Student student = studentService.getStudentById(id);
        return Result.success(student);
    }

    @PutMapping
    @LogOperation
    public Result updateStudent(@RequestBody Student student) {
        log.info("updateStudent,student:{}", student);
        studentService.updateStudent(student);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    @LogOperation
    public Result deleteStudentById(@PathVariable("ids") List<Integer> ids) {
        log.info("deleteStudentById,ids:{}", ids);
        studentService.deleteStudentByIds(ids);
        return Result.success();
    }

    @PutMapping("/violation/{id}/{score}")
    public Result updateStudentViolation(@PathVariable Integer id, @PathVariable Integer score) {
        log.info("updateStudentViolation,id:{},score:{}", id, score);
        studentService.updateStudentViolation(id,score);
        return Result.success();
    }
}
