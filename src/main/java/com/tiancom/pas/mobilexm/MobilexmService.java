package com.tiancom.pas.mobilexm;

import java.util.List;
import java.util.Map;

import com.tiancom.pas.common.framework.context.LoginUser;

public interface MobilexmService {

	final static String KEY = "MobilexmService";

	public void insertXmfx(Map map);
	
	public Integer selectMgsByYgbh(Map map);
	
	public void updateYgbhMgs(Map map);
	
	public void updateFxrMgs(Map map);
	
	public void insertFsjl(Map map);
	
	public String selectSmrByJldh(Map map);
	
	public List selectFcfxnr(Map map);
	
	public List selectFxxq(Map map);
	
	public List selectPlxq(Map map);
	
	public void insertFxpl(Map map);
	
	public void deleteFxjl(Map map);
	
	public void deletePljl(Map map);
	
	public List selectZtlist(Map map);
	
	public String insertZt(Map map);
	
	public List selectFclist(Map map);
	
	public List selectLbPlxq(Map map);
}
