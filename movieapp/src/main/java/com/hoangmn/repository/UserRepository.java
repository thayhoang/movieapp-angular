package com.hoangmn.repository;

import com.hoangmn.model.Role;
import com.hoangmn.model.User;
import com.hoangmn.repository.mapper.RoleMapper;
import com.hoangmn.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    public User findByUsername(String username) {
        User user = userMapper.findByUsername(username);
        if (user != null) {
            user.setRoles(roleMapper.getRoleForUser(user.getId()));
        }
        return user;
    }

    public boolean existsByUsername(String username) {
        return false;
    }

    public boolean existsByEmail(String email) {
        return false;
    }

    public void save(User user) {
        userMapper.save(user);
        Set<Role> roles = user.getRoles();
        roles.forEach(role -> {
            role.setId(roleMapper.findByName(role.getName().name()).getId());
        });
        for (Role role : roles) {
            roleMapper.save(user.getId(), role.getId());
        }
    }
}
