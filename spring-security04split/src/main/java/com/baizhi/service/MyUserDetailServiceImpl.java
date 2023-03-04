package com.baizhi.service;

import com.baizhi.entity.RoLe;
import com.baizhi.entity.User;
import com.baizhi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class MyUserDetailServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    @Autowired
    public MyUserDetailServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.查询用户
        User user = userMapper.getUserByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 2.获取角色
        List<RoLe> roles = userMapper.getRolesByUid(user.getId());
        user.setRoles(roles);
        return user;
    }
}
