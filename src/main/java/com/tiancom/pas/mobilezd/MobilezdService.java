package com.tiancom.pas.mobilezd;

import java.util.List;
import java.util.Map;

import com.tiancom.pas.common.framework.context.LoginUser;

public interface MobilezdService {

	final static String KEY = "MobilezdService";

	public void insertZd(Map map);
	
	public List selectZdXm(Map map);
	
	public List selectZdHeader(Map map);
	
	public List selectZdMx(Map map);
	
	public List selectBm(Map map);
	
	public List selectZdMxhz(Map map);
	
	public void insertBxd(Map map);
	
	public List selectZdByZdbh(Map map);
	
	public void updateZd(Map map);
	
	public void deleteZd(Map map);
}
