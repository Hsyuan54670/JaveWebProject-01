package com.hsyuan.tliaswebstduy.pojo;

import lombok.Data;

@Data
public class StudentQueryParam {
    private String name;
    private Integer clazzId;
    private String degree;
    private Integer pageSize=10;
    private Integer page=1;
}
