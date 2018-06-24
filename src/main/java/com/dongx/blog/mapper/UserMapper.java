package com.dongx.blog.mapper;

import com.dongx.blog.dto.UserRoleDTO;
import com.dongx.blog.entity.Role;
import com.dongx.blog.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    UserRoleDTO findByUsername(@Param("username") String username);
    
    int insertRoleWithUser(Map<String, Object> map);
}