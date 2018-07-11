package com.dongx.blog.service.es;

import com.dongx.blog.entity.Blog;
import com.dongx.blog.resposity.es.EsBlogRepository;
import com.dongx.blog.service.BlogService;
import com.dongx.blog.vo.BlogVo;
import com.dongx.blog.vo.EsBlogVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * EsBlogServiceImplTest
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-09 11:03
 * Modified by:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBlogServiceImplTest {

	@Resource
	private EsBlogService esBlogService;
	
	@Resource
	private BlogService blogService;
	
	@Test
	public void findAll() {
		esBlogService.findAll().getData();
	}
	
	@Test
	public void save() {
		EsBlogVo esBlogVo = new EsBlogVo();
		esBlogVo.setId("23456789");
		esBlogVo.setTitle("test elasticsearch");
		EsBlogVo result = esBlogService.saveOrUpdate(esBlogVo);
		System.out.println(result.toString());
	}
	
	@Test
	public void saveAll() {
		List<BlogVo> blogVoList = (List) blogService.findAll().getData();
		blogVoList.forEach(e -> {
			BlogVo blog = (BlogVo) ((Map) blogService.findOne(e.getId()).getData()).get("blog");
			EsBlogVo esVo = new EsBlogVo();
			BeanUtils.copyProperties(blog, esVo);
		});
	}

	@Test
	public void findTopFive() {
		esBlogService.findTopFive();
	}
}