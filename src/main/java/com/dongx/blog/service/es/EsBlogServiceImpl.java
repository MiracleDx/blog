package com.dongx.blog.service.es;

import com.dongx.blog.common.CommonStatus;
import com.dongx.blog.resposity.es.EsBlogRepository;
import com.dongx.blog.sys.ServerResponse;
import com.dongx.blog.vo.EsBlogVo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * EsBlogServiceImpl
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-07 18:33
 * Modified by:
 */
@Service
public class EsBlogServiceImpl implements EsBlogService {
	
	@Resource
	private EsBlogRepository esBlogRepository;

	@Override
	public ServerResponse findByKeyword(String keyword) {
		List<EsBlogVo> vos = esBlogRepository.findDistinctByTitleLikeOrDescriptionLikeOrContentLike(keyword, keyword, keyword);
		if (vos.size() > 0) {
			return ServerResponse.createBySuccess("检索成功", vos);
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
