package com.tiancom.pas.yjfk;

import java.util.Map;

import com.tiancom.pas.common.framework.ibatis.IBaseDAO;

public class YjfkServiceImpl implements YjfkService {

	private IBaseDAO iBaseDAO;

	public IBaseDAO getiBaseDAO() {
		return iBaseDAO;
	}

	public void setiBaseDAO(IBaseDAO iBaseDAO) {
		this.iBaseDAO = iBaseDAO;
	}

	public void insertYhfk(Map map) {
		this.iBaseDAO.insert("WM_insertYjfk", map);
	}

}
