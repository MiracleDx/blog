package com.dongx.blog.vo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;
import java.util.Date;

/**
 * EsBlogVo
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-09 9:08
 * Modified by:
 */
@Data
@Document(indexName = "blog", type = "blog")
public class EsBlogVo {
	
	@Id
	private String id;
	
	@Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String title;

	@Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String description;

	@Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String content;
	
	private Date createTime;
	
	private Integer likeNumber;
	
	private Integer replyNumber;
	
	private Integer flag;
	
}
