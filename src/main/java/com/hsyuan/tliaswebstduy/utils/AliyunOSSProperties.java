package com.hsyuan.tliaswebstduy.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss") // 从配置文件中读取前缀为 aliyun.oss 的属性
public class AliyunOSSProperties {
    private String endpoint;
    private String bucketName;
    private String region;
}
