package org.warehouse.schedulers;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.ClntStdinDAO;
import org.warehouse.configs.models.mapper.StdinDAO;
import org.warehouse.models.admin.clnt.ClntStdinVO;
import org.warehouse.models.stdin.StdinVO;

import java.time.LocalDate;
import java.util.List;

@Service
@Log
@RequiredArgsConstructor
public class ClntStdinScheduler {
	private final ClntStdinDAO clntStdinDAO;
	private final StdinDAO stdinDAO;

	//@Scheduled(cron="0 */5 * * * *")
	public void stdin() {
		List<ClntStdinVO> stdinList = clntStdinDAO.getList();

		for(int i = 0; i < stdinList.size(); i++) {
			StdinVO stdinVO = new StdinVO();
			stdinVO.setStdinDt(LocalDate.now());

			stdinVO.setClntCd(stdinList.get(i).getClntCd());
			stdinVO.setWactrCd(stdinList.get(i).getWactrCd());
			stdinVO.setLocCd(stdinList.get(i).getLocCd());
			stdinVO.setItemCd(stdinList.get(i).getItemCd());
			stdinVO.setBeforeStdin(stdinList.get(i).getBeforeStdin());
			stdinVO.setNormal(stdinList.get(i).getBeforeStdin());
			stdinVO.setFault(0L);
			stdinVO.setStatus("1");
			stdinVO.setRegNm(stdinList.get(i).getClntNm());
			stdinVO.setRemark("test");

			stdinList.get(i).setStatus(true);


			System.out.println(stdinVO);

			stdinDAO.insertHeaderStdin(stdinVO);
			stdinDAO.insertDetailStdin(stdinVO);
		}
		clntStdinDAO.updateStatus();
	}
}
