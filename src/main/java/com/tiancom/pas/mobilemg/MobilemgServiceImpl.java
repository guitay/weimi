package com.tiancom.pas.mobilemg;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tiancom.pas.common.framework.context.LoginUser;
import com.tiancom.pas.common.framework.ibatis.IBaseDAO;

public class MobilemgServiceImpl implements MobilemgService {

	private IBaseDAO ibaseDAO;

	public IBaseDAO getIbaseDAO() {
		return ibaseDAO;
	}

	public void setIbaseDAO(IBaseDAO ibaseDAO) {
		this.ibaseDAO = ibaseDAO;
	}

	public List selectMcount(Map map){
		return this.ibaseDAO.selectListByPara("Mobilemg_selectMcount", map);
		
	}

	public void updateMg(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilemg_updateMg", map);
	}
	
	public List getZjxx(){
		return this.ibaseDAO.selectListByPara("Mobilemg_getZjxx", null);
		
	}
	
	public void updateMg2(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilemg_updateMg2", map);
	}
	
	public void updateMg3(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilemg_updateMg3", map);
	}
	
	public void insertZjjl(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilemg_insertZjjl", map);
	}
	
	public List selectJplist(Map map){
		return this.ibaseDAO.selectListByPara("Mobilemg_selectJplist", map);
		
	}
	
}
