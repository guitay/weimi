package com.tiancom.pas.khsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tiancom.pas.common.framework.ibatis.IBaseDAO;

public class KhSearchServiceImpl implements KhSearchService {

	private IBaseDAO iBaseDAO;
	
	public IBaseDAO getiBaseDAO() {
		return iBaseDAO;
	}

	public void setiBaseDAO(IBaseDAO iBaseDAO) {
		this.iBaseDAO = iBaseDAO;
	}

	public List loadKh(String key) {
		List khList = iBaseDAO.selectListByPara("KhSearch_loadKh", null);
		return khList;
	}

	@Override
	public void saveKh(Map map) {
		// TODO Auto-generated method stub
		this.iBaseDAO.insert("KhSearch_saveKh", map);
	}

	@Override
	public String selectKhbhBykKhmc(Map map) {
		// TODO Auto-generated method stub
		return (String)iBaseDAO.selectByPrimaryKey("KhSearch_selectKhbhBykKhmc", map);
	}

}
