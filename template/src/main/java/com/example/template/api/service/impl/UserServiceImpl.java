package com.example.template.api.service.impl;

import com.example.template.api.entity.User;
import com.example.template.api.mapper.UserMapper;
import com.example.template.api.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author 黄宇
 * @since 2022-04-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
