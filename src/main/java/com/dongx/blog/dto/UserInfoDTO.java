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

	private String nickname;
	
	private String mobile;
	
	private String originPath;
	
	
}
