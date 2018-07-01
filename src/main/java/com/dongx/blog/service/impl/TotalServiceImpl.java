package com.dongx.blog.service.impl;

import com.dongx.blog.common.TotalStatusEnum;
import com.dongx.blog.entity.Total;
import com.dongx.blog.entity.TotalCount;
import com.dongx.blog.mapper.TotalCountMapper;
import com.dongx.blog.resposity.TotalRepository;
import com.dongx.blog.service.TotalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * TotalServiceImpl
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-01 11:14
 * Modified by:
 */
@Slf4j
@Service
public class TotalServiceImpl implements TotalService {
	
	@Resource
	private TotalRepository totalRepository;
	
	@Resource
	private TotalCountMapper totalCountMapper;
	
	@Override
	public void addStatus(String userId, String blogId) {
		Total total = new Total();
		total.setUserId(userId);
		total.setBlogId(blogId);
		total.setStatus(TotalStatusEnum.CONFIRM.getCode());
		totalRepository.save(total);
	}

	@Override
	public void cancelStatus(String userId, String blogId) {
		Total total = new Total();
		total.setUserId(userId);
		total.setBlogId(blogId);
		total.setStatus(TotalStatusEnum.CANCEL.getCode());
		totalRepository.save(total);
	}

	@Override
	public void addLikeCount(String blogId) {
		totalCountMapper.addLikeCount(blogId);
	}

	@Override
	public void decLikeCount(String blogId) {
		totalCountMapper.decLikeCount(blogId);
	}

	@Override
	public void addReplyCount(String blogId) {
		totalCountMapper.addReplyCount(blogId);
	}

	@Override
	public TotalCount findLikeAndReplyCount(String blogId) {
		TotalCount totalCount = totalCountMapper.findLikeAndReplyCount(blogId);
 		return totalCount;
	}
}
