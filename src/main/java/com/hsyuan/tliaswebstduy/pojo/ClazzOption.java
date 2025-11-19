package com.hsyuan.tliaswebstduy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClazzOption {
    private List clazzList=new ArrayList();
    private List dataList=new ArrayList();
}
