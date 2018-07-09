package com.dongx.blog.resposity.es;

import com.dongx.blog.vo.EsBlogVo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * EsBlogRepository
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-08 18:29
 * Modified by:
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlogVo, String> {
	
}
