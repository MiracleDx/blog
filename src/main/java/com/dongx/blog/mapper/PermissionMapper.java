package com.dongx.blog.mapper;

import com.dongx.blog.entity.Permission;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
    
    List<Permission> findAll();
    
    List<Permission> findByUserid(@Param("userid") String userid);
}