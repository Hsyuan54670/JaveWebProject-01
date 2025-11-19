package com.hsyuan.tliaswebstduy.mapper;

import com.hsyuan.tliaswebstduy.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    void insertBatch(List<EmpExpr> exprList);

    void deleteBatch(List<Integer> ids);
}
