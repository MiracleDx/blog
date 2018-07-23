package com.dongx.blog.vo;

import lombok.Data;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * BlogDTO
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-03 17:01
 * Modified by:
 */
@Data	
public class BlogVo implements Serializable {

	private String id;

	private String title;

	private String description;

	private Integer category;

	private String content;

	private String createUser;

	private Date createTime;

	private String createIp;

	private String updateUser;

	private Date updateTime;
	
	private String updateIp;

	private Integer status;
	
	private Integer isLike;
	
	private Integer likeNumber;
	
	private Integer replyNumber;
}
