package com.dongx.blog.vo;

import lombok.Data;

import java.util.Date;

/**
 * UserVo
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-21 20:19
 * Modified by:
 */
@Data
public class UserVo {

	private String id;

	private String username;

	private String nickname;

	private String mobile;

	private String avatar;

	private Date registerTime;

	private String registerIp;

	private Date updateTime;

	private Date loginTime;

	private String loginIp;
}
