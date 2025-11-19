package com.hsyuan.tliaswebstduy;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class EncodingTest {

    static {
        // 强制设置系统编码为UTF-8
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");
        
        try {
            // 重设System.out和System.err的编码
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
            System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8.name()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testChineseOutput() {
        System.out.println("=== 中文编码测试 ===");
        System.out.println("测试普通中文：你好，世界！");
        System.out.println("测试特殊字符：①②③④⑤");
        System.out.println("测试长文本：这是一个用于测试控制台中文输出的单元测试类。");
        System.out.println("测试混合内容：Hello World! 你好世界！ 123456");
        System.out.println("测试结束 ===");
    }

    @Test
    public void testSystemProperties() {
        System.out.println("=== 系统属性检查 ===");
        System.out.println("file.encoding: " + System.getProperty("file.encoding"));
        System.out.println("sun.jnu.encoding: " + System.getProperty("sun.jnu.encoding"));
        System.out.println("默认编码: " + System.getProperty("sun.stdout.encoding"));
        System.out.println("控制台编码测试完成");
    }
}