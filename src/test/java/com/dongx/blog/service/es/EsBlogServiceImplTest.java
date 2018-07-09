package com.dongx.blog.service.es;

import com.dongx.blog.vo.EsBlogVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Iterator;

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
	
	@Test
	public void findAll() {
		Iterable<EsBlogVo> all = esBlogService.findAll();
		Iterator<EsBlogVo> iterator = all.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next().toString());
		}
	}
	
	@Test
	public void save() {
		EsBlogVo esBlogVo = new EsBlogVo();
		esBlogVo.setId("23456789");
		esBlogVo.setTitle("test elasticsearch");
		EsBlogVo result = esBlogService.save(esBlogVo);
		System.out.println(result.toString());
	}
}