package com.tiancom.pas.mobilemg;

import java.util.List;
import java.util.Map;

import com.tiancom.pas.common.framework.context.LoginUser;

public interface MobilemgService {

	final static String KEY = "MobilemgService";

	public List selectMcount(Map map);
	
	public void updateMg(Map map);
	
	public List getZjxx();
	
	public void updateMg2(Map map);
	
	public void updateMg3(Map map);
	
	public void insertZjjl(Map map);
	
	public List selectJplist(Map map);
}
