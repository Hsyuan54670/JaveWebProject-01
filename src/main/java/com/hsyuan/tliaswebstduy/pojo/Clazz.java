package com.hsyuan.tliaswebstduy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clazz {
    /**
     * ID，主键（自增）
     * 数据库字段：id（int unsigned）
     */
    private Integer id;

    /**
     * 班级名称（非空，唯一）
     * 数据库字段：name（varchar(30)）
     */
    private String name;

    /**
     * 班级教室
     * 数据库字段：room（varchar(20)）
     */
    private String room;

    /**
     * 开课时间（非空）
     * 数据库字段：begin_date（date）
     * Java中用LocalDate映射数据库date类型
     */
    private LocalDate beginDate;

    /**
     * 结课时间（非空）
     * 数据库字段：end_date（date）
     */
    private LocalDate endDate;

    /**
     * 班主任ID（可空，关联员工表ID）
     * 数据库字段：master_id（int unsigned）
     */
    private Integer masterId;

    /**
     * 学科（非空）
     * 数据库字段：subject（tinyint unsigned）
     * 取值范围：1:java, 2:前端, 3:大数据, 4:Python, 5:Go, 6:嵌入式
     * 建议：实际开发中可定义枚举类替代Integer，增强类型安全
     */
    private Integer subject;

    /**
     * 创建时间
     * 数据库字段：create_time（datetime）
     * Java中用LocalDateTime映射数据库datetime类型
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     * 数据库字段：update_time（datetime）
     */
    private LocalDateTime updateTime;
    private String masterName;
}
