package com.baizhi.mapper;

import com.baizhi.entity.RoLe;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    User getUserByUsername(@Param("username") String username);

    // 根据用户id查询用户角色
    List<RoLe> getRolesByUid(@Param("uid") Integer uid);

    // 根据用户名更新密码
    int updateUserPasswordByUserName(@Param("username") String username, @Param("newPassword") String newPassword);
}
