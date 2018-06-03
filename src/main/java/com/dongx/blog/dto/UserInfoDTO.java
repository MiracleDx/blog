package com.dongx.blog.dto;

import com.dongx.blog.entity.Role;
import com.dongx.blog.entity.UserInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * UserInfoDTO
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-02 14:12
 * Modified by:
 */
@Data
public class UserInfoDTO {

	private Integer id;

	private String userId;

	private String nickname;

	private Integer mobile;

	private String avatar;

	private Date registerTime;

	private String registerIp;

	private Date updateTime;

	private Date loginTime;

	private String loginIp;
	
}
