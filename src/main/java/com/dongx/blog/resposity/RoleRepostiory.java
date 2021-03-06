package com.dongx.blog.resposity;

import com.dongx.blog.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RoleRepostiory
 *
 * @author: dongx
 * Description: 角色资源访问接口
 * Created in: 2018-06-01 14:11
 * Modified by:
 */
public interface RoleRepostiory extends JpaRepository<Role, Integer> {

	/**
	 * 通过角色名称查找出角色
	 * @param roleName
	 * @return
	 */
	Role findByRoleName(String roleName);
	
}
