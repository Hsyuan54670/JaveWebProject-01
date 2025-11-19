package com.hsyuan.tliaswebstduy.config;

import com.aliyun.oss.OSS;
import com.hsyuan.tliaswebstduy.utils.AliyunOSSProperties;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;

/**
 * 第三方Bean配置测试类
 * 验证第三方Bean是否正确注入IOC容器
 */
@SpringBootTest
@Slf4j
public class ThirdPartyConfigTest {



    @Autowired
    private Snowflake snowflake;

    @Autowired
    private ExecutorService fixedThreadPool;

    @Autowired
    private ExecutorService singleThreadExecutor;

    @Autowired
    private HttpUtil httpUtil;

    @Autowired
    private ThirdPartyConfig.ThirdPartyService thirdPartyService;


    /**
     * 测试雪花算法ID生成器
     */
    @Test
    public void testSnowflake() {
        log.info("=== 测试雪花算法ID生成器 ===");
        
        // 生成几个ID进行测试
        for (int i = 0; i < 5; i++) {
            long id = snowflake.nextId();
            log.info("生成的ID {}: {}", i + 1, id);
            assert id > 0 : "生成的ID必须大于0";
        }
        
        log.info("✅ 雪花算法ID生成器测试成功");
    }

    /**
     * 测试线程池Bean
     */
    @Test
    public void testThreadPools() throws InterruptedException {
        log.info("=== 测试线程池Bean ===");
        
        // 测试固定大小线程池
        log.info("固定线程池状态: {}", fixedThreadPool.isTerminated() ? "已终止" : "运行中");
        
        // 提交一个简单的任务
        fixedThreadPool.submit(() -> {
            log.info("固定线程池任务执行: 线程ID生成测试");
            long id = snowflake.nextId();
            log.info("任务中生成的ID: {}", id);
        });
        
        // 测试单线程执行器
        log.info("单线程执行器状态: {}", singleThreadExecutor.isTerminated() ? "已终止" : "运行中");
        
        singleThreadExecutor.submit(() -> {
            log.info("单线程执行器任务执行: 简单的日志输出");
        });
        
        // 等待任务完成
        Thread.sleep(1000);
        log.info("✅ 线程池Bean测试成功");
    }

    /**
     * 测试HTTP工具类Bean
     */
    @Test
    public void testHttpUtil() {
        log.info("=== 测试HTTP工具类Bean ===");
        
        // 验证Bean存在
        assert httpUtil != null : "HTTP工具类Bean不能为空";
        log.info("HTTP工具类类型: {}", httpUtil.getClass().getName());
        
        // 演示使用HTTP工具类（不实际发送请求）
        log.info("✅ HTTP工具类Bean注入成功");
    }

    /**
     * 测试自定义第三方服务
     */
    @Test
    public void testThirdPartyService() {
        log.info("=== 测试自定义第三方服务 ===");
        
        // 验证服务Bean存在
        assert thirdPartyService != null : "第三方服务Bean不能为空";
        
        // 调用服务方法
        String serviceInfo = thirdPartyService.getServiceInfo();
        log.info("服务信息: {}", serviceInfo);
        
        // 测试连接方法
        thirdPartyService.connect();
        
        log.info("✅ 自定义第三方服务测试成功");
    }

    /**
     * 综合测试：验证所有Bean都正确注入
     */
    @Test
    public void testAllBeansInjected() {
        log.info("=== 综合测试：验证所有第三方Bean注入 ===");
        assert snowflake != null : "雪花算法生成器未注入";
        assert fixedThreadPool != null : "固定线程池未注入";
        assert singleThreadExecutor != null : "单线程执行器未注入";
        assert httpUtil != null : "HTTP工具类未注入";
        assert thirdPartyService != null : "第三方服务未注入";
        log.info("✅ 所有第三方Bean都成功注入IOC容器！");
        log.info("=== 第三方Bean配置测试完成 ===");
    }
}