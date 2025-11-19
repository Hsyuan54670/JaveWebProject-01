package com.hsyuan.tliaswebstduy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hsyuan.tliaswebstduy.mapper.StudentMapper;
import com.hsyuan.tliaswebstduy.pojo.ClazzQueryParam;
import com.hsyuan.tliaswebstduy.pojo.PageResult;
import com.hsyuan.tliaswebstduy.pojo.Student;
import com.hsyuan.tliaswebstduy.pojo.StudentQueryParam;
import com.hsyuan.tliaswebstduy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public PageResult<Student> getStudents(StudentQueryParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Student> students = studentMapper.selectAll(param);
        Page<Student> p=(Page<Student>) students;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public void addStudent(Student student) {
        // 手动设置创建时间和修改时间
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.insert(student);
    }

    @Override
    public Student getStudentById(Integer id) {
        return studentMapper.findById(id);
    }

    @Override
    public void updateStudent(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
    }

    @Override
    public void deleteStudentByIds(List<Integer> ids) {
        studentMapper.deleteByIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStudentViolation(Integer id, Integer score) {
        Student student = studentMapper.findById(id);
        if (student == null) {
            throw new IllegalArgumentException("学生不存在");
        }
        student.setViolationScore(student.getViolationScore() + score);
        student.setViolationCount(student.getViolationCount() + 1);
        // 手动设置修改时间
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
    }
}
