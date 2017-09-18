package com.tiancom.pas.weixin.wxbd;

import java.util.Map;

import com.tiancom.pas.weixin.pojo.M_WXBD;

/**
 * 微信绑定
 * @author chenwx
 *
 */
//kuang.chun.hua 20170210
public interface WxbdService {

	final static String KEY = "wxbd.WxbdService";
	
	public M_WXBD  queryBdWxbd(String openid);
	
	public Integer queryBdYgXxById(String ygbh);

	public Integer queryBdYgXxByMm(Map map);
	
	public void update_WxbdXx(Map map);
	
}
