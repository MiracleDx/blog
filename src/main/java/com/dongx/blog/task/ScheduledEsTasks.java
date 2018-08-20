package com.dongx.blog.task;

import com.dongx.blog.common.CommonStatus;
import com.dongx.blog.common.TotalStatusEnum;
import com.dongx.blog.dto.BlogDTO;
import com.dongx.blog.entity.*;
import com.dongx.blog.mapper.TotalCountMapper;
import com.dongx.blog.resposity.BlogRepository;
import com.dongx.blog.resposity.TotalRepository;
import com.dongx.blog.resposity.UserRepository;
import com.dongx.blog.resposity.es.EsBlogRepository;
import com.dongx.blog.service.impl.BlogServiceImpl;
import com.dongx.blog.vo.BlogVo;
import com.dongx.blog.vo.EsBlogVo;
import com.dongx.blog.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * ScheduledEsTasks
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-25 21:43
 * Modified by:
 */
@Slf4j
@Component
public class ScheduledEsTasks {
	
	@Resource
	private BlogRepository blogRepository;
	
	@Resource
	private EsBlogRepository esBlogRepository;
	
	@Resource
	private TotalCountMapper totalCountMapper;
	
	/*@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
		System.out.println(Instant.now());
	}*/

	/**
	 * 每天00点同步
	 */
	@Scheduled(cron = "0 0 0 1-30 * ? ")
	public void synchronizeEs() {
		log.info("synchronize elasticsearch tasks start");
		log.info("synchronize time: {}", Date.from(Instant.now()));
		Instant startTime = Instant.now();
		List<EsBlogVo> esBlogVos = new ArrayList<>();
		// 获取所有blog
		List<Blog> blogs = blogRepository.findAll();
		// 将所有blog转为esblog
		BlogServiceImpl impl = new BlogServiceImpl();
		for (Blog blog : blogs) {
			Blog blogTemp = blogRepository.getOne(blog.getId());
			TotalCount likeAndReplyCount = totalCountMapper.findLikeAndReplyCount(blog.getId());

			if (blogTemp != null) {
				EsBlogVo esBlogVo = new EsBlogVo();
				BeanUtils.copyProperties(blog, esBlogVo);
				//esBlogVo.setContent(impl.readFile(blog.getAddress(), blog.getFilename()));
				esBlogVo.setReplyNumber(likeAndReplyCount.getReplyNumber());
				esBlogVo.setLikeNumber(likeAndReplyCount.getLikeNumber());
				esBlogVos.add(esBlogVo);
				esBlogVo.setFlag(CommonStatus.ACTIVE.getCode());
			}
		}
		//先清空之前所有记录
		esBlogRepository.deleteAll();
		esBlogRepository.saveAll(esBlogVos);
		Instant endTime = Instant.now();
		long millis = Duration.between(startTime, endTime).toMillis();
		log.info("synchronize elasticsearch tasks end");
		log.info("synchronize Elasticsearch: {}, spend time: {}", esBlogVos.size(), millis);
	}
}
