package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.user.UserVO;


import java.util.List;

@Mapper
public interface UserDAO {
	List<UserVO> getUserList(); // User 테이블 가져오기
	void insertUser(UserVO user); // 회원 가입
	UserVO getUserByEmail(String email); // 회원 정보 가져오기
	UserVO getUserById(String userId);
	//void updateUser(User user); // 회원 정보 수정
	//void deleteUser(String userId); // 회원 탈퇴
}
