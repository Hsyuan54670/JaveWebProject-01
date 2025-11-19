package com.hsyuan.tliaswebstduy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 编码配置类 - 解决Windows系统中文乱码问题
 * 强制设置系统编码为UTF-8
 */
@Configuration
@Slf4j
public class EncodingConfig implements ApplicationRunner {

    static {
        // 静态代码块，在类加载时执行
        fixSystemEncoding();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("=== 系统编码配置检查 ===");
        log.info("file.encoding: {}", System.getProperty("file.encoding"));
        log.info("sun.jnu.encoding: {}", System.getProperty("sun.jnu.encoding"));
        log.info("默认编码: {}", System.getProperty("sun.stdout.encoding"));
        
        // 再次确保编码正确
        fixSystemEncoding();
        
        log.info("=== 编码配置完成 ===");
    }

    /**
     * 修复系统编码设置
     */
    private static void fixSystemEncoding() {
        try {
            // 强制设置系统属性
            System.setProperty("file.encoding", "UTF-8");
            System.setProperty("sun.jnu.encoding", "UTF-8");
            
            // 使用反射强制设置编码字段（针对某些JVM版本）
            try {
                java.lang.reflect.Field charsetField = Charset.class.getDeclaredField("defaultCharset");
                charsetField.setAccessible(true);
                charsetField.set(null, StandardCharsets.UTF_8);
            } catch (Exception e) {
                // 忽略反射异常
            }
            
            // 重设标准输出流的编码
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
            System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
            
        } catch (Exception e) {
            System.err.println("编码设置失败: " + e.getMessage());
        }
    }
}