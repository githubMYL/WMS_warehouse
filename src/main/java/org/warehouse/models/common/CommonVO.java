package org.warehouse.models.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class CommonVO {

    private String title;       // 페이지제목

    private String pageName;    // 페이지이름

    private String menuCode;    // 메뉴코드이름

}
