package com.hsyuan.tliaswebstduy.mapper;

import com.hsyuan.tliaswebstduy.pojo.Clazz;
import com.hsyuan.tliaswebstduy.pojo.ClazzQueryParam;
import com.hsyuan.tliaswebstduy.pojo.PageResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClazzMapper {

    List<Clazz> selectAll(ClazzQueryParam param);

    void insert(Clazz clazz);

    void deleteById(Integer id);

    void updateById(Clazz clazz);

    Clazz findById(Integer id);

}
