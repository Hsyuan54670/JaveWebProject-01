package com.hsyuan.tliaswebstduy.filter;

import com.hsyuan.tliaswebstduy.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 会话跟踪 使用令牌
 * 令牌过滤器
 * 用于验证请求头中是否包含有效的令牌
 * 如果令牌为空或无效,则返回未授权错误响应
 * 如果令牌有效,则继续处理请求
 */
@Slf4j
//@WebFilter(urlPatterns = "/*") // 拦截所有请求
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 如果是登录请求,则直接放行
        if(request.getRequestURI().contains("/login")){
            log.info("登录请求,直接放行");
            filterChain.doFilter(request,response);
            return;
        }
        // 从请求头中获取令牌
        String token = request.getHeader("token");
        if(token==null||token.isEmpty()){
            // 如果令牌为空,则返回错误响应
            log.info("请求头中未包含token,返回未授权");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("未授权");
            return;
        }
        // 验证令牌
        try {
            JwtUtils.parseToken(token);
        } catch (Exception e) {
            // 如果令牌验证失败,则返回错误响应
            log.info("令牌验证失败,返回未授权");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("令牌无效");
            return;
        }
        // 令牌验证通过,继续处理请求
        log.info("令牌验证通过,继续处理请求");
        filterChain.doFilter(request,response);
    }
}
