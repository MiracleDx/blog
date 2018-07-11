package com.dongx.blog.resposity.es;

import com.dongx.blog.vo.EsBlogVo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * EsBlogRepository
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-08 18:29
 * Modified by:
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlogVo, String> {
	
	/**
	 * 按照点赞数排序，方便截取前五
	 * @param flag
	 * @return
	 */
	List<EsBlogVo> findEsBlogVosByFlagOrderByLikeNumberDesc(Integer flag);


	/**
	 * 按照发布时间排序，方便截取前五
	 * @param flag
	 * @return
	 */
	List<EsBlogVo> findEsBlogVosByFlagOrderByCreateTimeDesc(Integer flag);
	
	/**
	 * 检索包含keyword的信息
	 * @param title
	 * @param description
	 * @param content
	 * @return
	 */
	List<EsBlogVo> findDistinctByTitleContainingOrDescriptionContainingOrContentContaining(String title, String description, String content);
	
	List<EsBlogVo> findDistinctByTitleLikeOrDescriptionLikeOrContentLike(String title, String description, String content);
}
