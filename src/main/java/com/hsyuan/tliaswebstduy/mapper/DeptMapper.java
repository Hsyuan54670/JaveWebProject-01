package com.hsyuan.tliaswebstduy.mapper;

import com.hsyuan.tliaswebstduy.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DeptMapper {
// 1.   @Results({
//            @Result(property = "id", column = "id"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "createTime", column = "create_time"),
//            @Result(property = "updateTime", column = "update_time")
//    })
//    2.起别名@Select("select id, name, create_time createTime, update_time updateTime  from dept order by update_time desc")
//3.开启驼峰命名开关
    @Select("select id, name, create_time , update_time  from dept order by update_time desc")
    List<Dept> findAll();

    @Delete("delete from dept where id = #{id}")
    void deleteDeptById(Integer id);

    @Insert("insert into dept (name,create_time,update_time) values (#{name},#{createTime},#{updateTime})")
    void addDept(Dept dept);

    @Select("select id, name, create_time, update_time  from dept where id = #{id}")
    Dept getDeptNameById(Integer id);

    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void updateDept(Dept dept);
}
