package com.tiancom.pas.mobilelogin;

import org.apache.struts.action.ActionForm;

public class MobileLoginForm extends ActionForm{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String xmmc;
	
	public String getXmmc() {
		return xmmc;
	}
	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
