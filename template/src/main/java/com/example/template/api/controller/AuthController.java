package com.example.template.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.template.api.entity.Role;
import com.example.template.api.entity.User;
import com.example.template.api.entity.UserInfo;
import com.example.template.api.entity.UserRole;
import com.example.template.api.service.IRoleService;
import com.example.template.api.service.IUserInfoService;
import com.example.template.api.service.IUserRoleService;
import com.example.template.api.service.IUserService;
import com.example.template.common.entity.Login;
import com.example.template.common.entity.Password;
import com.example.template.common.entity.R;
import com.example.template.common.enums.StatusCode;
import com.example.template.common.exception.CommonException;
import com.example.template.common.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 鉴权 前端控制器
 * </p>
 *
 * @author 黄宇
 * @since 2022-04-21
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtils jwtUtils;
    private final IUserService iUserService;
    private final IUserInfoService iUserInfoService;
    private final IUserRoleService iUserRoleService;
    private final IRoleService iRoleService;

    @PostMapping("/login")
    public R<String> login(@RequestBody Login login) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String username = login.getUsername();
        String password = login.getPassword();
        queryWrapper.eq("username", username).eq("password", password);
        User user = iUserService.getOne(queryWrapper);
        if (ObjectUtils.isEmpty(user)) {
            throw new CommonException(StatusCode.USERNAME_OR_PASSWORD_ERROR);
        }
        Long state = user.getState();
        if (!state.equals(1L)) {
            throw new CommonException(StatusCode.USER_STATE_EXCEPTION);
        }
        Long id = user.getId();
        return R.success(jwtUtils.create(id, username));
    }

    @GetMapping("/user-roles")
    public R<List<Role>> roles() {
        Long id = jwtUtils.getId();
        List<UserRole> userRoles = iUserRoleService.list(new QueryWrapper<UserRole>().eq("user_id", id));
        if (ObjectUtils.isEmpty(userRoles)) {
            return R.success(Collections.emptyList());
        }
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()));
        List<Role> list = iRoleService.list(queryWrapper);
        return R.success(list);
    }

    @GetMapping("/user-info")
    public R<UserInfo> userInfo() {
        Long id = jwtUtils.getId();
        UserInfo userInfo = iUserInfoService.getById(id);
        return R.success(userInfo);
    }

    @PostMapping("/password")
    public R<Void> password(@RequestBody Password password) {
        Long id = jwtUtils.getId();
        String oldPassword = password.getOldPassword();
        String newPassword = password.getNewPassword();
        User user = iUserService.getOne(new QueryWrapper<User>().eq("id", id).eq("password", oldPassword));
        if (ObjectUtils.isEmpty(user)) {
            throw new CommonException(StatusCode.USER_PASSWORD_ERROR);
        }
        user.setPassword(newPassword);
        iUserService.updateById(user);
        return R.success();
    }

    @PostMapping("/logout")
    public R<Void> logout() {
        return R.success();
    }

    @PostMapping("/upload")
    public R<Void> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile, UserInfo userInfo) {
        log.info("file:{}", multipartFile);
        log.info("params:{}", userInfo);
        return R.success();
    }
}
