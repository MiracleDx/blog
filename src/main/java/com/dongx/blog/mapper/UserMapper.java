package com.dongx.blog.mapper;

import com.dongx.blog.dto.UserDTO;
import com.dongx.blog.entity.User;
import org.springframework.data.repository.query.Param;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    UserDTO findByUsername(@Param("username") String username);
}