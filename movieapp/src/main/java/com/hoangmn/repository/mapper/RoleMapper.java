package com.hoangmn.repository.mapper;


import com.hoangmn.model.Role;
import org.apache.ibatis.annotations.*;

import java.util.Set;


@Mapper
public interface RoleMapper {

    @Select("SELECT * FROM role WHERE name = #{name}")
    Role findByName(String name);

    @Select("SELECT r.* FROM user_roles ur INNER JOIN role r ON ur.role_id = r.id AND ur.user_id = #{userId} ")
    Set<Role> getRoleForUser(long userId);

    @Insert("INSERT INTO user_roles(user_id, role_id) values(#{userId}, #{roleId})")
    int save(Long userId, Integer roleId);
}
