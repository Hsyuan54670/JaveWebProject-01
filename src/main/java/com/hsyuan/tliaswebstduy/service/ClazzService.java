package com.hsyuan.tliaswebstduy.service;

import com.hsyuan.tliaswebstduy.pojo.Clazz;
import com.hsyuan.tliaswebstduy.pojo.ClazzQueryParam;
import com.hsyuan.tliaswebstduy.pojo.PageResult;

import java.util.List;

public interface ClazzService {

    PageResult<Clazz> getClazz(ClazzQueryParam param);

    List<Clazz> getClazzList(ClazzQueryParam param);

    void addClazz(Clazz clazz);

    void deleteClazz(Integer id);

    void updateClazz(Clazz clazz);

    Clazz getClazzById(Integer id);
}
