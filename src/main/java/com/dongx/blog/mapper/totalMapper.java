package com.dongx.blog.mapper;

import com.dongx.blog.entity.total;

public interface totalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(total record);

    int insertSelective(total record);

    total selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(total record);

    int updateByPrimaryKey(total record);
}