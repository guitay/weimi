package com.tiancom.pas.weixin.wxbd;

import java.util.Map;

import com.tiancom.pas.weixin.pojo.M_WXBD;
import com.tiancom.pas.common.framework.ibatis.IBaseDAO;

/**
 * 微信绑定
 * @author chenwx
 *
 */
//kuang.chun.hua 20170210
public class WxbdServiceImpl implements WxbdService {

	private IBaseDAO ibaseDAO;
	public IBaseDAO getIbaseDAO() {
		return ibaseDAO;
	}
	public void setIbaseDAO(IBaseDAO ibaseDAO) {
		this.ibaseDAO = ibaseDAO;
	}

	public M_WXBD queryBdWxbd(String ygbh){
		return (M_WXBD)ibaseDAO.selectByPrimaryKey("pas_queryBdWxbd", ygbh);
	}

	public Integer queryBdYgXxById(String ygbh){
		return (Integer)ibaseDAO.selectByPrimaryKey("pas_queryBdYgidTCount", ygbh);
	}

	public Integer queryBdYgXxByMm(Map map){
		return (Integer)ibaseDAO.selectByPrimaryKey("pas_queryBdDlmcTCount", map);
	}
	
	public void update_WxbdXx(Map map){
		ibaseDAO.deleteByPrimaryKey("maps_deleteUnsubscribe", map);
		ibaseDAO.insert("maps_insertSubscribe", map);
	}
	
}
