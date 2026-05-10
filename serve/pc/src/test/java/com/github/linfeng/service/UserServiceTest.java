package com.github.linfeng.service;

import com.github.linfeng.entity.Users;
import com.github.linfeng.utils.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private IUsersService mockUsersService;

    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        userService = new UserService();

        java.lang.reflect.Field serviceField = UserService.class.getDeclaredField("service");
        serviceField.setAccessible(true);
        serviceField.set(userService, mockUsersService);

        jwtTokenUtil = new JwtTokenUtil();
        java.lang.reflect.Field jwtField = UserService.class.getDeclaredField("jwtTokenUtil");
        jwtField.setAccessible(true);
        jwtField.set(userService, jwtTokenUtil);

        passwordEncoder = new BCryptPasswordEncoder();
        java.lang.reflect.Field encoderField = UserService.class.getDeclaredField("passwordEncoder");
        encoderField.setAccessible(true);
        encoderField.set(userService, passwordEncoder);
    }

    @Test
    public void testLoginWithValidCredentials() {
        String username = "testuser";
        String rawPassword = "123456";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Users mockUser = new Users();
        mockUser.setUserName(username);
        mockUser.setPwd(encodedPassword);

        when(mockUsersService.getByUsername(username)).thenReturn(mockUser);

        String token = userService.login(username, rawPassword);

        assertNotNull(token, "Token should not be null for valid credentials");
        assertFalse(token.isEmpty(), "Token should not be empty");
        verify(mockUsersService, times(1)).getByUsername(username);
    }

    @Test
    public void testLoginWithInvalidPassword() {
        String username = "testuser";
        String rawPassword = "123456";
        String wrongPassword = "wrongpassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Users mockUser = new Users();
        mockUser.setUserName(username);
        mockUser.setPwd(encodedPassword);

        when(mockUsersService.getByUsername(username)).thenReturn(mockUser);

        String token = userService.login(username, wrongPassword);

        assertNull(token, "Token should be null for invalid password");
        verify(mockUsersService, times(1)).getByUsername(username);
    }

    @Test
    public void testLoginWithNonexistentUser() {
        String username = "nonexistent";
        String rawPassword = "123456";

        when(mockUsersService.getByUsername(username)).thenReturn(null);

        String token = userService.login(username, rawPassword);

        assertNull(token, "Token should be null for nonexistent user");
        verify(mockUsersService, times(1)).getByUsername(username);
    }

    @Test
    public void testLoginWithNullUser() {
        String username = "testuser";
        String rawPassword = "123456";

        when(mockUsersService.getByUsername(username)).thenReturn(null);

        String token = userService.login(username, rawPassword);

        assertNull(token, "Token should be null when user is null");
    }

    @Test
    public void testListUsers() {
        Users user1 = new Users();
        user1.setUserName("user1");
        Users user2 = new Users();
        user2.setUserName("user2");

        when(mockUsersService.list()).thenReturn(java.util.Arrays.asList(user1, user2));

        java.util.List<Users> users = userService.list();

        assertNotNull(users, "User list should not be null");
        assertEquals(2, users.size(), "User list should contain 2 users");
        verify(mockUsersService, times(1)).list();
    }

    @Test
    public void testGetUserById() {
        Integer userId = 1;
        Users mockUser = new Users();
        mockUser.setUid(userId);
        mockUser.setUserName("testuser");

        when(mockUsersService.getById(userId)).thenReturn(mockUser);

        Users user = userService.getById(userId);

        assertNotNull(user, "User should not be null");
        assertEquals(userId, user.getUid(), "User ID should match");
        verify(mockUsersService, times(1)).getById(userId);
    }

    @Test
    public void testGetUserByIdNotFound() {
        Integer userId = 999;

        when(mockUsersService.getById(userId)).thenReturn(null);

        Users user = userService.getById(userId);

        assertNull(user, "User should be null for non-existent ID");
        verify(mockUsersService, times(1)).getById(userId);
    }
}