package com.hoangmn.repository.mapper;

import com.hoangmn.model.Role;
import com.hoangmn.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users(id, username, password, email) values(null, #{username}, #{password}, #{email})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int save(User user);

    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

}
