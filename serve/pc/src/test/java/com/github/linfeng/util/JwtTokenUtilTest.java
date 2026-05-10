package com.github.linfeng.util;

import com.github.linfeng.utils.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenUtilTest {

    private JwtTokenUtil createJwtWithExpiration(long expirationSeconds) {
        JwtTokenUtil jwt = new JwtTokenUtil();
        try {
            java.lang.reflect.Field secretField = JwtTokenUtil.class.getDeclaredField("secret");
            secretField.setAccessible(true);
            secretField.set(jwt, "test-secret-key");
            java.lang.reflect.Field expField = JwtTokenUtil.class.getDeclaredField("expirationSeconds");
            expField.setAccessible(true);
            expField.setLong(jwt, expirationSeconds);
        } catch (Exception e) {
            fail("Failed to set JwtTokenUtil fields: " + e.getMessage());
        }
        return jwt;
    }

    @Test
    public void testGenerateAndValidateToken() {
        JwtTokenUtil jwt = createJwtWithExpiration(3600);
        String username = "testUser";
        String token = jwt.generateToken(username);
        assertNotNull(token, "Token should not be null");
        assertEquals(username, jwt.getUsernameFromToken(token), "Username extracted should match");
        assertTrue(jwt.validateToken(token, username), "Token should be valid for the same user");
    }

    @Test
    public void testValidateTokenWithWrongUsername() {
        JwtTokenUtil jwt = createJwtWithExpiration(3600);
        String username = "testUser";
        String token = jwt.generateToken(username);
        assertFalse(jwt.validateToken(token, "differentUser"), "Token should be invalid for different user");
    }

    @Test
    public void testValidateTokenWithExpiredToken() {
        JwtTokenUtil jwt = createJwtWithExpiration(-1);
        String username = "testUser";
        String token = jwt.generateToken(username);
        assertFalse(jwt.validateToken(token, username), "Token should be invalid when expired");
    }

    @Test
    public void testValidateTokenWithMalformedToken() {
        JwtTokenUtil jwt = createJwtWithExpiration(3600);
        String malformedToken = "not.a.valid.jwt.token";
        assertFalse(jwt.validateToken(malformedToken, "testUser"), "Malformed token should be invalid");
    }

    @Test
    public void testValidateTokenWithNullToken() {
        JwtTokenUtil jwt = createJwtWithExpiration(3600);
        assertFalse(jwt.validateToken(null, "testUser"), "Null token should be invalid");
    }

    @Test
    public void testValidateTokenWithEmptyToken() {
        JwtTokenUtil jwt = createJwtWithExpiration(3600);
        assertFalse(jwt.validateToken("", "testUser"), "Empty token should be invalid");
    }

    @Test
    public void testGetUsernameFromTokenWithInvalidToken() {
        JwtTokenUtil jwt = createJwtWithExpiration(3600);
        assertThrows(Exception.class, () -> {
            jwt.getUsernameFromToken("invalid.token.here");
        }, "Invalid token should throw exception");
    }

    @Test
    public void testGenerateTokenForDifferentUsers() {
        JwtTokenUtil jwt = createJwtWithExpiration(3600);
        String token1 = jwt.generateToken("user1");
        String token2 = jwt.generateToken("user2");
        assertNotEquals(token1, token2, "Different users should get different tokens");
        assertEquals("user1", jwt.getUsernameFromToken(token1));
        assertEquals("user2", jwt.getUsernameFromToken(token2));
    }
}
