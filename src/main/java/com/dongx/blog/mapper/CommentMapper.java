package com.dongx.blog.mapper;

import com.dongx.blog.entity.Comment;
import com.dongx.blog.vo.CommentVo;

import java.util.List;
import java.util.Map;

public interface CommentMapper {
    int deleteByPrimaryKey(String id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<CommentVo> findAllByBlogIdOrUserIdAndStatusOrder(Map<String, Object> map);
    
    Integer findMaxFloorByBlogId(Map<String, Object> map);
}