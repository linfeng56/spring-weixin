package com.github.linfeng.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT Token 工具类，负责生成、解析和校验 Token。
 */
@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationSeconds;

    /**
     * 根据用户名生成 JWT Token。
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationSeconds * 1000);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    /**
     * 从 Token 中解析用户名。
     */
    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * 校验 Token 是否有效且未过期。
     */
    public boolean validateToken(String token, String username) {
        try {
            Claims claims = getClaims(token);
            String tokenUsername = claims.getSubject();
            return tokenUsername.equals(username) && !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}
