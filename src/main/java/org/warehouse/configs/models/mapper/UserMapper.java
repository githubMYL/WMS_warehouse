package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.user.User;

import java.util.List;

@Mapper
public interface UserMapper {
	List<User> getUserList(); // User 테이블 가져오기
	void insertUser(User user); // 회원 가입
	User getUserByEmail(String email); // 회원 정보 가져오기
	User getUserById(String userId);
	void updateUser(User user); // 회원 정보 수정
	void deleteUser(String userId); // 회원 탈퇴
}
