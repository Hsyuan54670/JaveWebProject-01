package com.hsyuan.tliaswebstduy.service;

import com.hsyuan.tliaswebstduy.pojo.ClazzQueryParam;
import com.hsyuan.tliaswebstduy.pojo.PageResult;
import com.hsyuan.tliaswebstduy.pojo.Student;
import com.hsyuan.tliaswebstduy.pojo.StudentQueryParam;

import java.util.List;

public interface StudentService {
    PageResult<Student> getStudents(StudentQueryParam param);

    void addStudent(Student student);

    Student getStudentById(Integer id);

    void updateStudent(Student student);

    void deleteStudentByIds(List<Integer> ids);

    void updateStudentViolation(Integer id, Integer score);
}
