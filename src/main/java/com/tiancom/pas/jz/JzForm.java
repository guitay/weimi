package com.tiancom.pas.jz;

import com.tiancom.pas.common.framework.struts.PasBaseForm;

public class JzForm extends PasBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8958616837624821460L;

	private Integer jzdh;// 订单编号 Integer 是
	private Integer xmbh;// 项目编号 Integer 否 Xmbh、xmmc二选一
	private String xmmc;// 项目名称 String 否
	private Integer khbh;// 客户编号 Integer 是
	private String khmc;// 客户名称 String 否
	private Integer ygid;// 乘机人 Integer 是
	private Integer ygbh;// 乘机人员工编号 String
	private String ygxm;// 乘机人姓名 String
	private String bmbh;// 乘机人所属部门编号 String
	private String bmmc;// 乘机人所属部门名称 String
	private String jzje;// 借支金额 String
	private Integer jzrq;// 出发时间 Date 是
	private String comment;// 备注 String 否
	private String procInstId;// 订票流程实例Id String 否
	private String taskId;// 订票流程任务Id String 否

	public Integer getJzdh() {
		return jzdh;
	}

	public void setJzdh(Integer jzdh) {
		this.jzdh = jzdh;
	}

	public Integer getXmbh() {
		return xmbh;
	}

	public void setXmbh(Integer xmbh) {
		this.xmbh = xmbh;
	}

	public String getXmmc() {
		return xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	public Integer getKhbh() {
		return khbh;
	}

	public void setKhbh(Integer khbh) {
		this.khbh = khbh;
	}

	public String getKhmc() {
		return khmc;
	}

	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}

	public Integer getYgid() {
		return ygid;
	}

	public void setYgid(Integer ygid) {
		this.ygid = ygid;
	}

	public Integer getYgbh() {
		return ygbh;
	}

	public void setYgbh(Integer ygbh) {
		this.ygbh = ygbh;
	}

	public String getYgxm() {
		return ygxm;
	}

	public void setYgxm(String ygxm) {
		this.ygxm = ygxm;
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

	public String getJzje() {
		return jzje;
	}

	public void setJzje(String jzje) {
		this.jzje = jzje;
	}

	public Integer getJzrq() {
		return jzrq;
	}

	public void setJzrq(Integer jzrq) {
		this.jzrq = jzrq;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
