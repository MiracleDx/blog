package com.dongx.blog.dto;

import com.dongx.blog.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * UserRoleDTO
 *
 * @author: dongx
 * Description:
 * Created in: 2018-05-27 14:16
 * Modified by:
 */
@Data
public class UserRoleDTO {

	private String id;

	private String username;

	private String password;

	private Integer status;
	
	private List<Role> roles;
}
