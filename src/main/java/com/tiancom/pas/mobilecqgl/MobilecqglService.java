package com.tiancom.pas.mobilecqgl;

import java.util.List;
import java.util.Map;

import com.tiancom.pas.common.framework.context.LoginUser;

public interface MobilecqglService {

	final static String KEY = "MobilecqglService";

	//获取当前日期的前x天中是否有填写考勤的数据
	public List selectRqysList(Map map);
	
	public List selectQdls(Map map);
	
	public List selectZdls(Map map);
	
	public List selectDpls(Map map);
	
	public String saveYgqd(Map map);
	
	public Integer selectCount(Map map);
	
	public List selectYgByYgbh(String ygbh);
	
	public List selectXmList(Map map);
	
	public List selectSfList(Map map);
	
	public Integer selectGzsj(Map map);
	
	public List selectQdls2(Map map);
	
	public List selectQdls3(Map map);
	
	public int deleteJl(Map map);
	
	public List selectXmlbList(Map map);
	
	public List selectXmxqList(Map map);
	
	public void insertGz(Map map);
	
	public void updateGz(Map map);
	
	public List selectXmzj(Map map);
	
	public List selectXmzxs(Map map);
	
	public List selectXmgcs(Map map);
	
	public List selectXmzc(Map map);
	
	public List selectXmzbnr(Map map);
	
	public List selectXmfxnr(Map map);
	
	public List selectJlnr(Map map);
	
	
}
