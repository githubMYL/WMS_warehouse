package org.warehouse.commons;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

public class Menus {
	public static List<MenuDetail> gets(String code){
		List<MenuDetail> menus = new ArrayList<>();

		// 게시판 하위 메뉴

			menus.add(new MenuDetail("baseinfo", "기본정보", "/baseinfo"));
			menus.add(new MenuDetail("stdin", "입고", "/stdin"));
			menus.add(new MenuDetail("rels", "출고", "/rels"));
			menus.add(new MenuDetail("tmstk", "재고", "/tmstk"));
			menus.add(new MenuDetail("admin", "관리자", "/admin"));


		return menus;
	}

	public static String getSubMenuCode(HttpServletRequest request) {
		String URI = request.getRequestURI();

		return URI.substring(URI.lastIndexOf("/")+1);
	}
}