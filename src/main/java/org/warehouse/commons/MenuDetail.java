package org.warehouse.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class MenuDetail {
	private String code;
	private String name;
	private String url;
}
