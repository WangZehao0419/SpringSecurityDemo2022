package com.baizhi.springsecurity03web.mapper;

import com.baizhi.springsecurity03web.entity.RoLe;
import com.baizhi.springsecurity03web.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User getUserByUsername(@Param("username") String username);

    List<RoLe> getRolesByUid(@Param("uid") Integer uid);

}
