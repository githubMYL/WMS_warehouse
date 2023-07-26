package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.warehouse.models.rels.relsAction.RelsVO;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface RelsDAO {

	RelsVO relsNoChk(RelsVO relsDt);    // 출고등록 헤더 RELS_H MAX값 확인
	void relsHinsert(RelsVO relsVO);    // 출고등록 헤더 insert
	void relsDinsert(RelsVO relsVO);    // 출고등록 디테일 insert
	void relsSinsert(RelsVO relsVO);    // 출고등록 할당 insert
	void relsUpdate(RelsVO relsVO);     // 출고등록 update
	String relsHdelChk(String deleteKey); // H 삭제 전 데이터확인
	String relsDdelChk(String deleteKey); // D 삭제 전 데이터확인
	String relsSdelChk(String deleteKey); // S 삭제 전 데이터확인
	List<RelsVO> relsList();    // 출고등록 리스트
	List<RelsVO> relsDetailList(String keyVal); // 출고등록 품목기준 레스트
	List<RelsVO> relsSubDetList(String keyVal); // 출곡등록 로케이션기준 리스트
	RelsVO relsUpdataInit(String keyVal); // 업데이트 팝업 데이터셋팅 (relsDNO == Y)
	RelsVO relsDetInsert(String keyVal); // 업데이트 팝업 데이터셋팅 (relsDNO == N)
	void relsHdelete(String deleteKey); // 출고 H 삭제유무 갱신
	void relsDdelete(String deleteKey); // 출고 D 삭제유무 갱신
	void relsSdelete(String deleteKey); // 출고 S 삭제유무 갱신

	void alloConf(String keyVal);
}