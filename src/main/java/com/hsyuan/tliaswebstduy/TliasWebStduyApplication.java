package com.hsyuan.tliaswebstduy;

import cn.hutool.core.bean.BeanDesc;
import com.hsyuan.tliaswebstduy.utils.AliyunOSSOperator;
import com.hsyuan.tliaswebstduy.utils.AliyunOSSProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

//@ServletComponentScan // 扫描所有的Servlet组件,包括Filter、Listener、Servlet
@SpringBootApplication
public class TliasWebStduyApplication {
    //调用第三方bean的方法,将其注册到IOC容器中
    //推荐写在配置类中
    /**
     * @Bean注解
     * 然后写一个返回该类实例对象的方法
     *
     */


    public static void main(String[] args) {
        SpringApplication.run(TliasWebStduyApplication.class, args);
    }

}
