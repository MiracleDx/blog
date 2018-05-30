package com.dongx.blog.dto;

import com.dongx.blog.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * UserDTO
 *
 * @author: dongx
 * Description:
 * Created in: 2018-05-27 14:16
 * Modified by:
 */
@Data
public class UserDTO {

	private String id;

	private String username;

	private String password;

	private Integer status;
	
	private List<Role> roles;
}
