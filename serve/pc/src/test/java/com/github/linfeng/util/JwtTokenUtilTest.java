package com.github.linfeng.util;

import com.github.linfeng.utils.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtTokenUtilTest {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Test
    public void testGenerateAndValidateToken() {
        JwtTokenUtil jwt = new JwtTokenUtil();
        // manually set fields via reflection (since @Value not injected in plain test)
        try {
            java.lang.reflect.Field secretField = JwtTokenUtil.class.getDeclaredField("secret");
            secretField.setAccessible(true);
            secretField.set(jwt, "test-secret-key");
            java.lang.reflect.Field expField = JwtTokenUtil.class.getDeclaredField("expirationSeconds");
            expField.setAccessible(true);
            expField.setLong(jwt, 3600);
        } catch (Exception e) {
            fail("Failed to set JwtTokenUtil fields: " + e.getMessage());
        }
        String username = "testUser";
        String token = jwt.generateToken(username);
        assertNotNull(token, "Token should not be null");
        assertEquals(username, jwt.getUsernameFromToken(token), "Username extracted should match");
        assertTrue(jwt.validateToken(token, username), "Token should be valid for the same user");
    }
}
