package com.tiancom.pas.mobilelogin;

import java.util.List;
import java.util.Map;

public interface MobileLoginService {
	final static String key = "mobilelogin.MobileLoginService";
	
	//根据登录名查询用户
	public Rsglygxx selectByDlmc(String dlmc);
	
	//根据员工ID查询用户
	public Rsglygxx selectByYgid(String Ygid);
	
	
	public Integer selectKqts(Map map);
	
	
	public List selectKqfList(Map map);
}
