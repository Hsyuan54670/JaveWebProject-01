package com.hsyuan.tliaswebstduy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 控制台编码测试类
 * 用于验证中文输出是否正常显示
 */
@SpringBootTest
@Slf4j
public class ConsoleEncodingTest {

    @Test
    public void testSystemEncoding() {
        System.out.println("=== 系统编码信息 ===");
        System.out.println("file.encoding: " + System.getProperty("file.encoding"));
        System.out.println("sun.jnu.encoding: " + System.getProperty("sun.jnu.encoding"));
        System.out.println("默认控制台编码: " + System.getProperty("console.encoding"));
        
        // 强制设置编码
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");
        
        System.out.println("强制设置后编码:");
        System.out.println("file.encoding: " + System.getProperty("file.encoding"));
        System.out.println("sun.jnu.encoding: " + System.getProperty("sun.jnu.encoding"));
        System.out.println();
    }

    @Test
    public void testChineseOutput() {
        System.out.println("=== 中文输出测试 ===");
        System.out.println("普通中文：你好，世界！");
        System.out.println("特殊字符：①②③④⑤⑥⑦⑧⑨⑩");
        System.out.println("长文本测试：这是一个用于验证控制台中文编码的测试类，如果这些文字正常显示，说明编码问题已解决。");
        System.out.println("混合内容：Hello World! 你好世界！ 123456 @#$%^&*()");
        System.out.println();
    }

    @Test
    public void testLogOutput() {
        log.info("=== SLF4J日志中文输出测试 ===");
        log.info("普通中文日志：用户登录成功");
        log.warn("警告中文日志：参数验证失败");
        log.error("错误中文日志：数据库连接异常");
        log.info("长文本日志：这是一个包含中文的详细日志信息，用于测试日志系统的编码处理能力。");
        System.out.println();
    }

    @Test
    public void testMyBatisLogOutput() {
        System.out.println("=== MyBatis SQL日志测试 ===");
        System.out.println("模拟SQL日志：SELECT * FROM 用户表 WHERE 姓名 = '张三'");
        System.out.println("模拟参数日志：Parameters: 张三(String)");
        System.out.println("模拟结果日志：Total: 1");
        System.out.println();
    }

    @Test
    public void comprehensiveTest() {
        System.out.println("=== 综合编码测试开始 ===");
        
        testSystemEncoding();
        testChineseOutput();
        testLogOutput();
        testMyBatisLogOutput();
        
        System.out.println("=== 综合编码测试结束 ===");
        System.out.println("如果以上所有中文内容都正常显示，说明控制台编码问题已解决！");
    }
}