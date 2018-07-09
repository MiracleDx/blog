package com.dongx.blog.service.es;

import com.dongx.blog.resposity.es.EsBlogRepository;
import com.dongx.blog.sys.ServerResponse;
import com.dongx.blog.vo.EsBlogVo;
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
	public Iterable<EsBlogVo> findAll() {
		return esBlogRepository.findAll();
	}

	@Override
	public ServerResponse findTopFive() {
		return null;
	}

	@Override
	public ServerResponse findNewFive() {
		return null;
	}

	@Override
	public void delete() {

	}

	@Override
	public EsBlogVo update() {
		return null;
	}

	@Override
	public EsBlogVo save(EsBlogVo esBlogVo) {
		return esBlogRepository.save(esBlogVo);
	}
}
