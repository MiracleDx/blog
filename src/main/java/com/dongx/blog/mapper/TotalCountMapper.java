package com.dongx.blog.mapper;

import com.dongx.blog.entity.TotalCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TotalCountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TotalCount record);

    int insertSelective(TotalCount record);

    TotalCount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TotalCount record);

    int updateByPrimaryKey(TotalCount record);

    Integer addLikeCount(@Param("blogId") String blogId);

    Integer decLikeCount(@Param("blogId") String blogId);

    Integer addReplyCount(@Param("blogId") String blogId);

    TotalCount findLikeAndReplyCount(@Param("blogId") String blogId);
    
    List<TotalCount> findAllOfLikeAndReplyCount();
}