package com.dongx.blog.service.es;

import com.dongx.blog.sys.ServerResponse;
import com.dongx.blog.vo.EsBlogVo;

import java.util.List;

/**
 * EsBlogService
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-07 18:33
 * Modified by:
 */
public interface EsBlogService {

	/**
	 * 查询所有
	 * @return
	 */
	Iterable<EsBlogVo> findAll();

	/**
	 * 查询点赞量前5
	 * @return
	 */
	ServerResponse findTopFive();

	/**
	 * 查询最新5篇
	 * @return
	 */
	ServerResponse findNewFive();

	/**
	 * 删除一条数据
	 */
	void delete();

	/**
	 * 更新一条数据
	 * @return
	 */
	EsBlogVo update();

	/**
	 * 保存一条数据
	 * @return
	 */
	EsBlogVo save(EsBlogVo esBlogVo);
}
