package com.hsyuan.tliaswebstduy.mapper;

import com.hsyuan.tliaswebstduy.pojo.ClazzQueryParam;
import com.hsyuan.tliaswebstduy.pojo.Student;
import com.hsyuan.tliaswebstduy.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    List<Student> selectAll(StudentQueryParam param);

    void insert(Student student);

    Student findById(Integer id);

    void update(Student student);

    void deleteByIds(List<Integer> ids);

    @MapKey("name")
    List<Map<String, Object>> countStudentDegreeData();
    @MapKey("name")
    List<Map<String, Object>> countStudentClazzData();

    List<Student> selectAllById(Integer id);
}
