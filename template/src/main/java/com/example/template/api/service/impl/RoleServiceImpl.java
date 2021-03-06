package com.example.template.api.service.impl;

import com.example.template.api.entity.Role;
import com.example.template.api.mapper.RoleMapper;
import com.example.template.api.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author 黄宇
 * @since 2022-04-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
