package com.dongx.blog.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * ScheduledEsTasksTest
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-25 22:19
 * Modified by:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduledEsTasksTest {
	
	@Resource
	private ScheduledEsTasks scheduledEsTasks;

	@Test
	public void synchronizeEs() {
		scheduledEsTasks.synchronizeEs();
	}
}