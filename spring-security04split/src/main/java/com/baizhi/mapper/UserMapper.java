package com.baizhi.mapper;

import com.baizhi.entity.RoLe;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User getUserByUsername(@Param("username") String username);

    List<RoLe> getRolesByUid(@Param("uid") Integer uid);

}
