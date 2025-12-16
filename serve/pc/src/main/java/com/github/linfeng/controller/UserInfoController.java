package com.github.linfeng.controller;

import com.github.linfeng.entity.Users;
import com.github.linfeng.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserInfoController {

    @Autowired
    @Qualifier("miniIUserService")
    private IUsersService usersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String[] SENSITIVE_FIELDS = {"pwd", "password", "openid", "accessToken",
            "accessTokenExpire", "refreshToken", "access_token", "refresh_token"};

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Unauthorized"));
        }

        Users user = usersService.getByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "User not found"));
        }

        return ResponseEntity.ok(filterSensitiveFields(user));
    }

    @PutMapping("/info")
    public ResponseEntity<?> updateUserInfo(@RequestBody Map<String, Object> updates) {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Unauthorized"));
        }

        Users user = usersService.getByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "User not found"));
        }

        if (updates.containsKey("nickName")) {
            user.setNickName((String) updates.get("nickName"));
        }
        if (updates.containsKey("phone")) {
            user.setPhone((String) updates.get("phone"));
        }
        if (updates.containsKey("email")) {
            String email = (String) updates.get("email");
            if (email != null && !email.isEmpty()) {
                Users existingUser = usersService.getByUsername(username);
                if (existingUser != null && email.equals(existingUser.getEmail())) {
                    user.setEmail(email);
                } else {
                    return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Email already in use"));
                }
            }
        }
        if (updates.containsKey("avatar")) {
            user.setAvatar((String) updates.get("avatar"));
        }

        usersService.update(user);
        return ResponseEntity.ok(filterSensitiveFields(user));
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> passwordData) {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Unauthorized"));
        }

        String oldPassword = passwordData.get("oldPassword");
        String newPassword = passwordData.get("newPassword");

        if (oldPassword == null || newPassword == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Missing password"));
        }

        Users user = usersService.getByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "User not found"));
        }

        if (!passwordEncoder.matches(oldPassword, user.getPwd())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Invalid old password"));
        }

        user.setPwd(passwordEncoder.encode(newPassword));
        usersService.update(user);

        return ResponseEntity.ok(Collections.singletonMap("message", "Password changed successfully"));
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null) {
            return auth.getPrincipal().toString();
        }
        return null;
    }

    private Map<String, Object> filterSensitiveFields(Users user) {
        Map<String, Object> result = new HashMap<>();
        result.put("uid", user.getUid());
        result.put("userName", user.getUserName());
        result.put("nickName", user.getNickName());
        result.put("phone", user.getPhone());
        result.put("email", user.getEmail());
        result.put("avatar", user.getAvatar());
        result.put("sex", user.getSex());
        result.put("birthday", user.getBirthday());
        result.put("domicile", user.getDomicile());
        result.put("nativePlace", user.getNativePlace());
        result.put("education", user.getEducation());
        result.put("profession", user.getProfession());
        return result;
    }
}
