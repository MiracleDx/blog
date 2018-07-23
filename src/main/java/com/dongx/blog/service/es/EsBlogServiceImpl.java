package com.dongx.blog.service.es;

import com.dongx.blog.common.CommonStatus;
import com.dongx.blog.resposity.es.EsBlogRepository;
import com.dongx.blog.sys.ServerResponse;
import com.dongx.blog.vo.EsBlogVo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * EsBlogServiceImpl
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-07 18:33
 * Modified by:
 */
@Slf4j
@Service
public class EsBlogServiceImpl implements EsBlogService {
	
	@Resource
	private EsBlogRepository esBlogRepository;

	@Override
	public ServerResponse findByKeyword(String keyword) {
		List<EsBlogVo> vos = esBlogRepository.findDistinctByTitleLikeOrDescriptionLikeOrContentLike(keyword.trim(), keyword.trim(), keyword.trim());
		if (vos.size() > 0) {
			return ServerResponse.createBySuccess("检索成功, 检索到" + vos.size() + "条数据", vos);
		}
		return ServerResponse.createBySuccess("没有检索到相关信息");
	}

	@Override
	public ServerResponse findAll() {
		Sort sort = new Sort(Sort.Direction.DESC, "createTime");
		return ServerResponse.createBySuccess(esBlogRepository.findAll(sort));
	}

	@Override
	public ServerResponse findTopFive() {
		// 截取点赞前五
		List<EsBlogVo> vos = esBlogRepository.findEsBlogVosByFlagOrderByLikeNumberDesc(CommonStatus.ACTIVE.getCode());
		if (vos.size() > 5) {
			List<EsBlogVo> subList = vos.subList(0, 5);
			return ServerResponse.createBySuccess(subList);
		}
		return ServerResponse.createBySuccess(vos);
	}

	@Override
	public ServerResponse findNewFive() {
		//截取发表前五
		List<EsBlogVo> vos = esBlogRepository.findEsBlogVosByFlagOrderByCreateTimeDesc(CommonStatus.ACTIVE.getCode());
		if (vos.size() > 5) {
			List<EsBlogVo> subList = vos.subList(0, 5);
			return ServerResponse.createBySuccess(subList);
		}
		return ServerResponse.createBySuccess(vos);
	}

	@Override
	public EsBlogVo findOne(String id) {
		return esBlogRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(String id) {
		esBlogRepository.deleteById(id);
	}

	@Override
	public EsBlogVo saveOrUpdate(EsBlogVo esBlogVo) {
		esBlogVo.setFlag(CommonStatus.ACTIVE.getCode());
		return esBlogRepository.save(esBlogVo);
	}
}
