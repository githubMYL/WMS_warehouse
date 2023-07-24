package org.warehouse.schedulers;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.DlyCnpDAO;


/**
 * 0. Project : WMS 프로젝트
 *
 * 1. FileName : DlyCnpScheduler.java
 * 2. Package : org.warehouse.schedulers
 * 3. Comment : 일수불 스케쥴러클래스
 * 4. 작성자 : 임동영
 * 5. 작성일 : 2023-07-19
 * 6. 변경이력 :
 * 이름 : 일자 : 근거자료 : 변경내용
 * ------------------------------------------------------
 *  임동영 : 2023-07-19 : : 신규 개발.
 */

@Service @Log
@RequiredArgsConstructor
public class DlyCnpScheduler {
    private final DlyCnpDAO dlyCnpDAO;

    @Scheduled(cron = "0 4 * * *")  // 매일 새벽 4
    private void dlyCnp() {
        dlyCnpDAO.dlycnpScheduler();
    }
}
