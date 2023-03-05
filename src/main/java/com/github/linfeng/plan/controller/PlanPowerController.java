package com.github.linfeng.plan.controller;

import java.util.List;
import com.github.linfeng.plan.entity.Role;
import com.github.linfeng.plan.holder.LoginUserHolder;
import com.github.linfeng.plan.service.IRoleService;
import com.github.linfeng.plan.view.LoginUser;
import com.github.linfeng.plan.view.ResponseView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 权限管理控制器
 */
@RequestMapping("/plan/power/")
@Controller
public class PlanPowerController extends BasePlanController {

    @Autowired
    private IRoleService roleService;

    /**
     * 角色列表
     *
     * @param model 模型
     * @return 页面文件路径
     */
    @GetMapping("/roles")
    public String roles(Model model) {
        LoginUser loginUser = LoginUserHolder.getLoginUser();
        model.addAttribute("admin", loginUser);

        List<Role> roles = roleService.list();
        model.addAttribute("pageRoles", roles);
        return "plan/power/roles";
    }

    /**
     * 编辑角色
     *
     * @param id    角色编号
     * @param model 模型
     * @return 页面文件路径
     */
    @GetMapping("roles/edit/{id}")
    public String rolesEdit(@PathVariable("id") Integer id, Model model) {
        LoginUser loginUser = LoginUserHolder.getLoginUser();
        model.addAttribute("admin", loginUser);

        Role role = roleService.getById(id);
        model.addAttribute("item", role);
        return "plan/power/roles-edit";
    }

    /**
     * 编辑角色
     *
     * @param id    角色编号
     * @param model 模型
     * @return 页面文件路径
     */
    @PostMapping("roles/edit/{id}")
    @ResponseBody
    public ResponseView<Integer> rolesDoEdit(@PathVariable("id") Integer id, Model model,
        String role, String description, Boolean available) {
        LoginUser loginUser = LoginUserHolder.getLoginUser();
        model.addAttribute("admin", loginUser);

        if (available == null) {
            available = false;
        }
        Role item = new Role();
        item.setRole(role);
        item.setDescription(description);
        item.setAvailable(available);
        Integer updateRows = roleService.update(id, item);
        if (updateRows > 0) {
            return new ResponseView<>(200, "操作成功", id);
        } else {
            return new ResponseView<>(500, "操作失败", id);
        }
    }

}
