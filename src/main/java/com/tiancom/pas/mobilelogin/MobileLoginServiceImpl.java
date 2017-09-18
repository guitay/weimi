package com.tiancom.pas.mobilelogin;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.dao.support.DaoSupport;

import com.tiancom.pas.common.framework.ibatis.IBaseDAO;
import com.tiancom.pas.login.XtbXtcs;
import com.tiancom.pas.login1.DataSource.Grid;
import com.tiancom.pas.login1.DataSource.Node;

public class MobileLoginServiceImpl implements MobileLoginService{
	
	private IBaseDAO iBaseDAO;
	
	public IBaseDAO getiBaseDAO() {
		return iBaseDAO;
	}

	public void setiBaseDAO(IBaseDAO baseDAO) {
		iBaseDAO = baseDAO;
	}

	@Override
	public Rsglygxx selectByDlmc(String dlmc) {
		// TODO Auto-generated method stub
		return (Rsglygxx)iBaseDAO.selectByPrimaryKey("selectByDlmc2",dlmc);
		 
	}

	public Integer selectKqts(Map map) {
		return (Integer) iBaseDAO.selectByPrimaryKey("cqgl_selectKqts", map);
	}

	@Override
	public List selectKqfList(Map map) {
		// 加载考勤信息
		return iBaseDAO.selectInfoByPara("kqgl_selectKqfList", map);
	}
	
	public Rsglygxx selectByYgid(String ygid) {
		// TODO Auto-generated method stub
		return (Rsglygxx)iBaseDAO.selectByPrimaryKey("selectByYgid",ygid);
		 
	}
}
