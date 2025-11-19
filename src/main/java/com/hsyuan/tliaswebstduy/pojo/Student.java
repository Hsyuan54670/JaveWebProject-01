package com.hsyuan.tliaswebstduy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    /**
     * ID，主键（自增）
     * 数据库字段：id（int unsigned）
     */
    private Integer id;

    /**
     * 姓名（非空）
     * 数据库字段：name（varchar(10)）
     */
    private String name;

    /**
     * 学号（非空，唯一）
     * 数据库字段：no（char(10)）
     */
    private String no;

    /**
     * 性别（非空）
     * 数据库字段：gender（tinyint unsigned）
     * 取值范围：1:男, 2:女
     * 建议：使用枚举类（如GenderEnum）替代Integer，避免硬编码
     */
    private Integer gender;

    /**
     * 手机号（非空，唯一）
     * 数据库字段：phone（varchar(11)）
     */
    private String phone;

    /**
     * 身份证号（非空，唯一）
     * 数据库字段：id_card（char(18)）
     */
    private String idCard;

    /**
     * 是否来自院校（非空）
     * 数据库字段：is_college（tinyint unsigned）
     * 取值范围：1:是, 0:否
     * 建议：使用枚举类（如IsCollegeEnum）替代Integer，增强可读性
     */
    private Integer isCollege;

    /**
     * 联系地址（可空）
     * 数据库字段：address（varchar(100)）
     */
    private String address;

    /**
     * 最高学历（可空）
     * 数据库字段：degree（tinyint unsigned）
     * 取值范围：1:初中, 2:高中, 3:大专, 4:本科, 5:硕士, 6:博士
     * 建议：使用枚举类（如DegreeEnum）替代Integer，避免数字歧义
     */
    private Integer degree;

    /**
     * 毕业时间（可空）
     * 数据库字段：graduation_date（date）
     * Java中用LocalDate映射数据库date类型
     */
    private LocalDate graduationDate;

    /**
     * 班级ID（非空，关联班级表ID）
     * 数据库字段：clazz_id（int unsigned）
     */
    private Integer clazzId;

    /**
     * 违纪次数（非空，默认0）
     * 数据库字段：violation_count（tinyint unsigned default '0'）
     */
    private Integer violationCount=0;

    /**
     * 违纪扣分（非空，默认0）
     * 数据库字段：violation_score（tinyint unsigned default '0'）
     */
    private Integer violationScore=0;

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
    private String clazzName;
}
