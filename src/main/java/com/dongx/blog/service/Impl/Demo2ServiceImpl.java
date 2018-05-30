package com.dongx.blog.service.Impl;

import com.dongx.blog.entity.Demo2;
import com.dongx.blog.mapper.Demo2Mapper;
import com.dongx.blog.service.Demo2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Demo2ServiceImpl
 *
 * @author: dongx
 * Description:
 * Created in: 2018-05-13 19:32
 * Modified by:
 */
@Service
public class Demo2ServiceImpl implements Demo2Service {

	@Autowired
	private Demo2Mapper demo2Mapper;
	
	@Override
	public Integer insert(Demo2 demo2) {
		return demo2Mapper.insert(demo2);
	}
}
