package com.hsyuan.tliaswebstduy.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.hsyuan.tliaswebstduy.utils.AliyunOSSProperties;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 第三方Bean配置类
 * 演示如何将第三方库的Bean引入Spring IOC容器
 */
//@Configuration
//@Slf4j
//public class ThirdPartyConfig {
//
//
//    /**
//     * 配置Hutool雪花算法ID生成器
//     * 演示如何配置第三方工具类作为Bean
//     */
//    @Bean
//    @Primary
//    public Snowflake snowflake() {
//        // 使用Hutool的雪花算法，workerId和datacenterId可以根据需要配置
//        long workerId = 1L;
//        long datacenterId = 1L;
//        log.info("初始化雪花算法ID生成器，workerId: {}, datacenterId: {}", workerId, datacenterId);
//        return IdUtil.getSnowflake(workerId, datacenterId);
//    }
//
//    /**
//     * 配置线程池Bean
//     * 演示如何配置Java原生对象作为Bean
//     */
//    @Bean(name = "fixedThreadPool")
//    public ExecutorService fixedThreadPool() {
//        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
//        log.info("初始化固定大小线程池，核心线程数: {}", corePoolSize);
//        return Executors.newFixedThreadPool(corePoolSize);
//    }
//
//    /**
//     * 配置单线程执行器
//     * 演示如何配置不同类型的Bean
//     */
//    @Bean(name = "singleThreadExecutor")
//    public ExecutorService singleThreadExecutor() {
//        log.info("初始化单线程执行器");
//        return Executors.newSingleThreadExecutor();
//    }
//
//    /**
//     * 配置HTTP客户端工具类（静态方法包装）
//     * 演示如何包装静态方法为Bean
//     */
//    @Bean
//    public HttpUtil httpUtil() {
//        // Hutool的HttpUtil是静态工具类，这里返回实例用于依赖注入
//        // 实际使用时可以直接调用静态方法，这里演示Bean配置
//        log.info("配置HTTP工具类Bean");
//        return new HttpUtil();
//    }
//
//    /**
//     * 配置自定义第三方服务客户端（示例）
//     * 演示如何创建自定义的第三方服务Bean
//     */
//    @Bean
//    public ThirdPartyService thirdPartyService() {
//        log.info("初始化第三方服务客户端");
//        return new ThirdPartyService();
//    }
//
//    /**
//     * 第三方服务客户端示例类
//     * 内部类，用于演示第三方Bean的配置
//     */
//    public static class ThirdPartyService {
//
//        private String serviceUrl = "https://api.thirdparty.com";
//
//        public String getServiceInfo() {
//            return "第三方服务客户端，服务地址: " + serviceUrl;
//        }
//
//        public void connect() {
//            log.info("连接到第三方服务: {}", serviceUrl);
//        }
//    }
//}