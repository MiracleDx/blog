package com.dongx.blog.service.es;

import com.dongx.blog.sys.ServerResponse;
import com.dongx.blog.vo.EsBlogVo;

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
	 * 按照关键字检索
	 * @param keyword
	 * @return
	 */
	ServerResponse findByKeyword(String keyword);

	/**
	 * 查询所有
	 * @return
	 */
	ServerResponse findAll();

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
	 * 查找一条数据
	 * @param id
	 * @return
	 */
	EsBlogVo findOne(String id);

	/**
	 * 删除一条数据
	 */
	void delete(String id);

	/**
	 * 更新或保存一条数据
	 * @return
	 */
	EsBlogVo saveOrUpdate(EsBlogVo esBlogVo);
}
