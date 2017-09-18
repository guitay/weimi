package com.tiancom.pas.mobilexm;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tiancom.pas.common.framework.context.LoginUser;
import com.tiancom.pas.common.framework.ibatis.IBaseDAO;

public class MobilexmServiceImpl implements MobilexmService {

	private IBaseDAO ibaseDAO;

	public IBaseDAO getIbaseDAO() {
		return ibaseDAO;
	}

	public void setIbaseDAO(IBaseDAO ibaseDAO) {
		this.ibaseDAO = ibaseDAO;
	}

	
	public void insertXmfx(Map map) {
		Integer Mgcount = ibaseDAO.selectCountRows("Mobilecqgl_selectMgcount", map);
		if (Mgcount>0) {//蜜罐表有数据则更新
			Integer wsjmds = ibaseDAO.selectCountRows("Mobilecqgl_selectWsjmds", map);
			if (wsjmds<10) {
				this.ibaseDAO.updateByPrimaryKey("Mobilecqgl_updateMg", map);
			}
		}else {//无数据则新增
			this.ibaseDAO.insert("Mobilecqgl_insertMg", map);
		}
		this.ibaseDAO.updateByPrimaryKey("Mobilexm_insertXmfx", map);
	}
	
	public Integer selectMgsByYgbh(Map map){
		
		Integer mgs = this.ibaseDAO.selectCountRows("Mobilexm_selectMgsCountByYgbh", map);
		
		if (mgs>0) {
			mgs = this.ibaseDAO.selectCountRows("Mobilexm_selectMgsByYgbh", map);
		}
		return mgs;
		
	}

	@Override
	public void updateYgbhMgs(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilexm_updateYgbhMgs", map);
		
	}

	@Override
	public void updateFxrMgs(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilexm_updateFxrMgs", map);
		
	}

	@Override
	public void insertFsjl(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilexm_insertFsjl", map);
		
	}
	
	public String selectSmrByJldh(Map map){
		return this.ibaseDAO.selectByPrimaryKey("Mobilexm_selectSmrByJldh", map).toString();
	}
	
	public List selectFcfxnr(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilexm_selectFcfxnr", map);
	}
	
	public List selectFxxq(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilexm_selectFxxq", map);
	}
	
	public List selectPlxq(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilexm_selectPlxq", map);
	}
	
	public void insertFxpl(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilexm_insertFxpl", map);
		
	}
	
	public void deleteFxjl(Map map) {
		Integer wsjmds = ibaseDAO.selectCountRows("Mobilecqgl_selectWsjmds", map);
		if (wsjmds>0) {
			this.ibaseDAO.updateByPrimaryKey("Mobilecqgl_updateMg2", map);
		}
		this.ibaseDAO.deleteByPrimaryKey("Mobilexm_deleteFxjl", map);//删除分享记录
		this.ibaseDAO.deleteByPrimaryKey("Mobilexm_deleteFxPljl", map);//删除分享对应的评论记录
	}
	
	public void deletePljl(Map map) {
		this.ibaseDAO.deleteByPrimaryKey("Mobilexm_deletePljl", map);
		
	}
	
	public List selectZtlist(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilexm_selectZtlist", map);
	}
	
	public String insertZt(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilexm_insertZt", map);
		return this.ibaseDAO.selectByPrimaryKey("Mobilexm_selectZtbh", map).toString();
	}
	
	public List selectFclist(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilexm_selectFclist", map);
	}
	
	public List selectLbPlxq(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilexm_selectLbPlxq", map);
	}
}
