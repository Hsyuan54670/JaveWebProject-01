package com.hsyuan.tliaswebstduy;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    private static final String SECRET_KEY_STRING = "ThisIsAVeryLongSecretKeyThatIsSecureEnoughForHS256Algorithm1234567890";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    // 完整的加密解密测试
    @Test
    public void testJwtEncryptAndDecrypt(){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("username","user123");
        dataMap.put("id",1001);
        dataMap.put("role","admin");
        
        // 生成JWT
        String jwt = generateToken(dataMap);
        System.out.println("生成的JWT: " + jwt);
        
        // 验证JWT
        Claims claims = parseToken(jwt);
        System.out.println("解密后的数据:");
        System.out.println("用户名: " + claims.get("username"));
        System.out.println("用户ID: " + claims.get("id"));
        System.out.println("角色: " + claims.get("role"));
        System.out.println("过期时间: " + claims.getExpiration());
    }
    
    // 生成JWT令牌
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1小时过期
                .signWith(SECRET_KEY, Jwts.SIG.HS256)
                .compact();
    }
    
    // 解析JWT令牌
    private Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}