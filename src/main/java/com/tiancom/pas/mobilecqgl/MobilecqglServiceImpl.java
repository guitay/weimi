package com.tiancom.pas.mobilecqgl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tiancom.pas.common.framework.context.LoginUser;
import com.tiancom.pas.common.framework.ibatis.IBaseDAO;

public class MobilecqglServiceImpl implements MobilecqglService {

	private IBaseDAO ibaseDAO;

	public IBaseDAO getIbaseDAO() {
		return ibaseDAO;
	}

	public void setIbaseDAO(IBaseDAO ibaseDAO) {
		this.ibaseDAO = ibaseDAO;
	}

	public List selectRqysList(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilecqgl_selectRqysList", map);
	}
	
	public List selectQdls(Map map) {
		List list=ibaseDAO.selectInfoByPara("Mobilecqgl_selectQdls", map);
		/*List list2=new ArrayList();
		for(int i=0;i<list.size();i++){
				Map a=(Map)list.get(i);
				a.put("count",list.size());
				list2.add(a);
		}*/
		return list;
	}
	
	public List selectZdls(Map map) {
		List list=ibaseDAO.selectInfoByPara("Mobilecqgl_selectZdls", map);
		
		return list;
	}
	
	public List selectDpls(Map map) {
		List list=ibaseDAO.selectInfoByPara("Mobilecqgl_selectDpls", map);
		
		return list;
	}
	
	public String saveYgqd(Map map){
		Integer Lscount = selectCount(map);//签到历史
		if(Lscount>0){
			this.ibaseDAO.deleteByPrimaryKey("Mobilecqgl_deleteQdls", map);
		}else {
			Integer Mgcount = ibaseDAO.selectCountRows("Mobilecqgl_selectMgcount", map);
			if (Mgcount>0) {//蜜罐表有数据则更新
				Integer wsjmds = ibaseDAO.selectCountRows("Mobilecqgl_selectWsjmds", map);
				if (wsjmds<10) {
					this.ibaseDAO.updateByPrimaryKey("Mobilecqgl_updateMg", map);
				}
			}else {//无数据则新增
				this.ibaseDAO.insert("Mobilecqgl_insertMg", map);
			}
		}
		this.ibaseDAO.insert("Mobilecqgl_insertYgqd", map);
		return "保存成功";
	}
	
	public Integer selectCount(Map map) {
		return ibaseDAO.selectCountRows("Mobilecqgl_selectCount", map);
	}
	
	public List selectYgByYgbh(String ygbh) {
		return (List)ibaseDAO.selectListByPara("Mobilecqgl_selectYgByYgbh", ygbh);
	}
	public List selectXmList(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilecqgl_selectXm", map);
	}
	public List selectSfList(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilecqgl_selectSf", map);
	}
	
	public Integer selectGzsj(Map map){
		return (Integer)ibaseDAO.selectByPrimaryKey("Mobilecqgl_selectGzsj", map);
	}
	
	public List selectQdls2(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilecqgl_selectQdls2", map);
	}
	
	public List selectQdls3(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilecqgl_selectQdls3", map);
	}
	
	public int deleteJl(Map map){
		Integer wsjmds = ibaseDAO.selectCountRows("Mobilecqgl_selectWsjmds", map);
		if (wsjmds>0) {
			this.ibaseDAO.updateByPrimaryKey("Mobilecqgl_updateMg2", map);
		}
		
		return this.ibaseDAO.deleteByPrimaryKey("Mobilecqgl_deleteJl", map);
	}
	
	public List selectXmlbList(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilecqgl_selectXmlb", map);
	}
	
	public List selectXmxqList(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilecqgl_selectXmxq", map);
	}
	
	public void insertGz(Map map) {
		ibaseDAO.insert("Mobilecqgl_insertGz", map);
	}
	
	public void updateGz(Map map) {
		ibaseDAO.updateByPrimaryKey("Mobilecqgl_updateGz", map);
	}
	
	public List selectXmzj(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilecqgl_selectXmzj", map);
	}
	
	public List selectXmzxs(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilecqgl_selectXmzxs", map);
	}
	
	public List selectXmgcs(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilecqgl_selectXmgcs", map);
	}
	
	public List selectXmzc(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilecqgl_selectXmzc", map);
	}
	
	public List selectXmzbnr(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilecqgl_selectXmzbnr", map);
	}
	
	public List selectXmfxnr(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilecqgl_selectXmfxnr", map);
	}
	
	public List selectJlnr(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilecqgl_selectJlnr", map);
	}
	
	
}
