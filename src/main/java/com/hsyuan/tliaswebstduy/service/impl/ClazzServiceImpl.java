package com.hsyuan.tliaswebstduy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hsyuan.tliaswebstduy.mapper.ClazzMapper;
import com.hsyuan.tliaswebstduy.mapper.StudentMapper;
import com.hsyuan.tliaswebstduy.pojo.*;
import com.hsyuan.tliaswebstduy.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public PageResult<Clazz> getClazz(ClazzQueryParam param) {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<Clazz> clazzList = clazzMapper.selectAll(param);
        Page<Clazz> p = (Page<Clazz>) clazzList;
        return new PageResult<>(p.getTotal(),p.getResult());
    }

    @Override
    public List<Clazz> getClazzList(ClazzQueryParam param) {

        return clazzMapper.selectAll(param);
    }

    @Override
    public void addClazz(Clazz clazz) {
        // 手动设置创建时间和修改时间
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.insert(clazz);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteClazz(Integer id) {
        clazzMapper.deleteById(id);
        // 级联删除学生
        // 先查询班级下的所有学生
        List<Student> students = studentMapper.selectAllById(id);
        if(students.isEmpty()){
            return;
        }
        // 提取学生ID
        List<Integer> studentIds = students.stream().map(Student::getId).toList();
        // 级联删除学生
        studentMapper.deleteByIds(studentIds);
    }


    @Override
    public void updateClazz(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.updateById(clazz);
    }

    @Override
    public Clazz getClazzById(Integer id) {
        return clazzMapper.findById(id);
    }
}
