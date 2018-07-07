package com.dongx.blog.dto;

import lombok.Data;

/**
 * UserPasswordDTO
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-05 20:49
 * Modified by:
 */
@Data
public class UserPasswordDTO {
	
	private String oldPassword;
	
	private String newPassword;
	
}
