package com.github.linfeng.admin.controller;

import com.github.linfeng.admin.entity.SysPermission;
import com.github.linfeng.admin.entity.SysRole;
import com.github.linfeng.admin.service.ISysPermissionService;
import com.github.linfeng.admin.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class SysAdminController {

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPermissionService permissionService;

    @GetMapping("/roles")
    public ResponseEntity<?> listRoles() {
        List<SysRole> roles = roleService.list();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<?> getRole(@PathVariable Integer id) {
        SysRole role = roleService.getById(id);
        if (role == null) {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "Role not found"));
        }
        return ResponseEntity.ok(role);
    }

    @PostMapping("/roles")
    public ResponseEntity<?> createRole(@RequestBody SysRole role) {
        Integer id = roleService.add(role);
        return ResponseEntity.ok(Collections.singletonMap("roleId", id));
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Integer id, @RequestBody SysRole role) {
        roleService.update(id, role);
        return ResponseEntity.ok(Collections.singletonMap("message", "Updated successfully"));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Integer id) {
        roleService.delete(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Deleted successfully"));
    }

    @GetMapping("/permissions")
    public ResponseEntity<?> listPermissions() {
        List<SysPermission> permissions = permissionService.list();
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/roles/{id}/permissions")
    public ResponseEntity<?> getRolePermissions(@PathVariable Integer id) {
        List<SysPermission> permissions = permissionService.listByRoleId(id);
        return ResponseEntity.ok(permissions);
    }
}
