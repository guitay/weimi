package com.tiancom.pas.weixin.pojo;

public class M_WXBD {

	//绑定的微信唯一号
	private String openid;
	//绑定的微信号
	private String wxh;
	//绑定的微信名称
	private String wxmc;
	//绑定的部门编号
	private String bmbh;
	//绑定的部门名称
	private String bmmc;
	//绑定的员工编号
	private String ygbh;
	//绑定的员工名称
	private String ygmc;
	//绑定日期
	private String bdrq;
	
	public String getBdrq() {
		return bdrq;
	}
	public void setBdrq(String bdrq) {
		this.bdrq = bdrq;
	}
	public String getBmbh() {
		return bmbh;
	}
	public void setBmbh(String bmbh) {
		this.bmbh = bmbh;
	}
	public String getBmmc() {
		return bmmc;
	}
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
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
	public String getYgmc() {
		return ygmc;
	}
	public void setYgmc(String ygmc) {
		this.ygmc = ygmc;
	}
	
}
