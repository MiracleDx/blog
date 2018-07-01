package com.dongx.blog.mapper;

import com.dongx.blog.entity.Total;

public interface TotalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Total record);

    int insertSelective(Total record);

    Total selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Total record);

    int updateByPrimaryKey(Total record);
}