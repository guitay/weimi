package com.tiancom.pas.weixin.wxbd;

import com.tiancom.pas.common.framework.struts.PasBaseForm;
//kuang.chun.hua 20170210
public class WxbdForm  extends  PasBaseForm{
	private static final long serialVersionUID = 1L;
	
	private String openid;
	private String wxh;
	private String wxmc;
	private String ygbh;
	private String dlmm;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	public String getWxh() {
		return wxh;
	}
	public void setWxh(String wxh) {
		this.wxh = wxh;
	}
	public String getWxmc() {
		return wxmc;
	}
	public void setWxmc(String wxmc) {
		this.wxmc = wxmc;
	}
	public String getYgbh() {
		return ygbh;
	}
	public void setYgbh(String ygbh) {
		this.ygbh = ygbh;
	}
	public String getDlmm() {
		return dlmm;
	}
	public void setDlmm(String dlmm) {
		this.dlmm = dlmm;
	}
}
