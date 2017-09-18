package com.tiancom.pas.khsearch;

import java.util.List;
import java.util.Map;

/**
 * 客户名录
 * 
 * @author NongFei
 *
 */
public interface KhSearchService {

	final static String KEY = "KhSearch.KhSearchService";

	
	public List loadKh(String key);
	
	public void saveKh(Map map);

	public String selectKhbhBykKhmc(Map map);
}
