package com.dongx.blog.resposity;

import com.dongx.blog.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserInfoRepository
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-02 15:17
 * Modified by:
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

	/**
	 * 通过用户id查询用户信息
	 * @param userId
	 * @return
	 */
	UserInfo findUserInfoByUserId(String userId);
}
