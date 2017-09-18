package com.tiancom.pas.jz;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.tiancom.pas.activiti.bean.TaskFlow;
import com.tiancom.pas.common.result.Model;

/**
 * 借支单
 * 
 * @author NongFei
 *
 */
public class Jzd extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2545670326544051917L;
	private Integer jzdh;// 订单编号 Integer 是
	private Integer xmbh;// 项目编号 Integer 否 Xmbh、xmmc二选一
	private String xmmc;// 项目名称 String 否
	private Integer khbh;// 客户编号 Integer 是
	private String khmc;// 客户名称 String 否
	private Integer ygid;// 借支人 Integer 是
	private String ygbh;// 借支人员工编号 String
	private String ygxm;// 借支人姓名 String
	private String bmbh;// 借支人所属部门编号 String
	private String bmmc;// 借支人所属部门名称 String
	private Integer jzbm;//借支部门ID
	private String jzbmbh;//借支部门编号
	private String jzbmmc;//借支部门名称
	
	private BigDecimal jzje;// 借支金额 String
	private Integer jzrq;// 出发时间 Date 是
	private String comment;// 备注 String 否
	private String procInstId;// 订票流程实例Id String 否
	private String taskId;// 订票流程任务Id String 否

	private List<TaskFlow> taskFlow;
	
	private boolean backJzd;//是否为被退回的借支单

	private String flowDesc;// 流程描述

	public boolean isBackJzd() {
		return backJzd;
	}

	public void setBackJzd(boolean backJzd) {
		this.backJzd = backJzd;
	}

	public Integer getJzbm() {
		return jzbm;
	}

	public void setJzbm(Integer jzbm) {
		this.jzbm = jzbm;
	}

	public String getJzbmbh() {
		return jzbmbh;
	}

	public void setJzbmbh(String jzbmbh) {
		this.jzbmbh = jzbmbh;
	}

	public String getJzbmmc() {
		return jzbmmc;
	}

	public void setJzbmmc(String jzbmmc) {
		this.jzbmmc = jzbmmc;
	}

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

	public String getYgbh() {
		return ygbh;
	}

	public void setYgbh(String ygbh) {
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

	public BigDecimal getJzje() {
		return jzje;
	}

	public void setJzje(BigDecimal jzje) {
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

	public List<TaskFlow> getTaskFlow() {
		return taskFlow;
	}

	public void setTaskFlow(List<TaskFlow> taskFlow) {
		this.taskFlow = taskFlow;
	}

	public String getFlowDesc() {
		return flowDesc;
	}

	public void setFlowDesc(String flowDesc) {
		this.flowDesc = flowDesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
