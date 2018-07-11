package com.dongx.blog.service.impl;

import com.dongx.blog.common.TotalStatusEnum;
import com.dongx.blog.entity.Total;
import com.dongx.blog.entity.TotalCount;
import com.dongx.blog.mapper.TotalCountMapper;
import com.dongx.blog.resposity.TotalRepository;
import com.dongx.blog.security.JwtUser;
import com.dongx.blog.service.TotalService;
import com.dongx.blog.service.es.EsBlogService;
import com.dongx.blog.sys.ServerResponse;
import com.dongx.blog.utils.UserUtils;
import com.dongx.blog.vo.EsBlogVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Server;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Resource
	private EsBlogService esBlogService;
	
	@Override
	@Transactional
	public ServerResponse addStatus(String blogId) {
		JwtUser user = UserUtils.getUser();
		Total temp = totalRepository.findByUserIdAndBlogId(user.getId(), blogId);
		// 之前有点赞记录
		if (temp != null) {
			temp.setStatus(TotalStatusEnum.CONFIRM.getCode());
			Total result = totalRepository.save(temp);
			if (result != null) {
				// 增加点赞总数
				totalCountMapper.addLikeCount(blogId);

				TotalCount likeAndReplyCount = totalCountMapper.findLikeAndReplyCount(blogId);
				EsBlogVo esBlogVo = esBlogService.findOne(blogId);
				esBlogVo.setLikeNumber(likeAndReplyCount.getLikeNumber());
				esBlogService.saveOrUpdate(esBlogVo);
				log.info("elasticsearch update {} likeNumber success", esBlogVo.getTitle());
						
				return ServerResponse.createBySuccess("点赞成功");
			}
			return ServerResponse.createByError("点赞失败， 请联系管理员");
		}
		
		Total total = new Total();
		total.setUserId(user.getId());
		total.setBlogId(blogId);
		total.setStatus(TotalStatusEnum.CONFIRM.getCode());
		Total result = totalRepository.save(total);
		if (result != null) {
			// 增加点赞总数
			totalCountMapper.addLikeCount(blogId);

			TotalCount likeAndReplyCount = totalCountMapper.findLikeAndReplyCount(blogId);
			EsBlogVo esBlogVo = esBlogService.findOne(blogId);
			esBlogVo.setLikeNumber(likeAndReplyCount.getLikeNumber());
			esBlogService.saveOrUpdate(esBlogVo);
			log.info("elasticsearch update {} likeNumber success", esBlogVo.getTitle());
			return ServerResponse.createBySuccess("点赞成功");
		}
		return ServerResponse.createByError("点赞失败， 请联系管理员");
	}

	@Override
	@Transactional
	public ServerResponse cancelStatus(String blogId) {
		JwtUser user = UserUtils.getUser();
		Integer rowCount = totalRepository.cancel(TotalStatusEnum.CANCEL.getCode(), user.getId(), blogId);
		if (rowCount > 0) {
			// 减少点赞总数
			TotalCount totalCount = totalCountMapper.findLikeAndReplyCount(blogId);
			// 点赞数不能小于0
			if (totalCount.getLikeNumber() > 0) {
				totalCountMapper.decLikeCount(blogId);
			}

			TotalCount likeAndReplyCount = totalCountMapper.findLikeAndReplyCount(blogId);
			EsBlogVo esBlogVo = esBlogService.findOne(blogId);
			esBlogVo.setLikeNumber(likeAndReplyCount.getLikeNumber());
			esBlogService.saveOrUpdate(esBlogVo);
			log.info("elasticsearch update blogId: {} likeNumber success", esBlogVo.getId());
			return ServerResponse.createBySuccess("取消点赞成功");
		}
		return ServerResponse.createByError("取消点赞失败， 请联系管理员");
	}

	@Override
	public void addReplyCount(String blogId) {
		totalCountMapper.addReplyCount(blogId);
		TotalCount likeAndReplyCount = totalCountMapper.findLikeAndReplyCount(blogId);
		EsBlogVo esBlogVo = esBlogService.findOne(blogId);
		esBlogVo.setReplyNumber(likeAndReplyCount.getReplyNumber());
		esBlogService.saveOrUpdate(esBlogVo);
		log.info("elasticsearch update blogId: {} replyNumber success", esBlogVo.getId());
	}

	@Override
	public TotalCount findLikeAndReplyCount(String blogId) {
 		return totalCountMapper.findLikeAndReplyCount(blogId);
	}
}
