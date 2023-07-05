package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.stdin.StdinVO;
import org.warehouse.models.user.UserVO;


import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserDAO {
	List<UserVO> getUserList(); // UserManage 테이블 가져오기
	void insertUser(UserVO user); // 회원 가입
	UserVO getUserByEmail(String email); // 회원 정보 가져오기
	UserVO getUserById(String userId);

	List<UserVO> getListByConditions(String clntNm, String userId, String userNm, String tel);
}
