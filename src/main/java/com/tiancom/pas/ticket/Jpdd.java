package com.tiancom.pas.ticket;

import java.util.Date;
import java.util.List;

import com.tiancom.pas.activiti.bean.TaskFlow;
import com.tiancom.pas.common.result.Model;

/**
 * 机票订单
 * 
 * @author NongFei
 *
 */
public class Jpdd extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2545670326544051917L;
	private Integer ddbh;// 订单编号 Integer 是
	private Integer xmbh;// 项目编号 Integer 否 Xmbh、xmmc二选一
	private String xmmc;// 项目名称 String 否
	private Integer khbh;// 客户编号 Integer 是
	private String khmc;// 客户名称 String 否
	private Integer ygid;// 乘机人 Integer 是
	private Integer ygbh;// 乘机人员工编号 String
	private String ygxm;// 乘机人姓名 String
	private String bmbh;// 乘机人所属部门编号 String
	private String bmmc;// 乘机人所属部门名称 String
	private String cfd;// 出发地 String 是
	private String mdd;// 目的的 String 是
	private String cfsj;// 出发时间 Date 是
	private String jyhb;// 建议航班 String 否
	private String comment;// 备注 String 否
	private String cpxx;// 出票信息 String 否
	private String procInstId;// 订票流程实例Id String 否
	private String taskId;// 订票流程任务Id String 否

	private List<TaskFlow> taskFlow;

	private String flowDesc;// 流程描述
	
	private boolean backDd;//是否为被退回的借支单
	
	

	public boolean isBackDd() {
		return backDd;
	}

	public void setBackDd(boolean backDd) {
		this.backDd = backDd;
	}

	public String getFlowDesc() {
		return flowDesc;
	}

	public Integer getDdbh() {
		return ddbh;
	}

	public void setDdbh(Integer ddbh) {
		this.ddbh = ddbh;
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

	public String getCfd() {
		return cfd;
	}

	public void setCfd(String cfd) {
		this.cfd = cfd;
	}

	public String getMdd() {
		return mdd;
	}

	public void setMdd(String mdd) {
		this.mdd = mdd;
	}

	public String getCfsj() {
		return cfsj;
	}

	public void setCfsj(String cfsj) {
		this.cfsj = cfsj;
	}

	public String getJyhb() {
		return jyhb;
	}

	public void setJyhb(String jyhb) {
		this.jyhb = jyhb;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCpxx() {
		return cpxx;
	}

	public void setCpxx(String cpxx) {
		this.cpxx = cpxx;
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

	public void setFlowDesc(String flowDesc) {
		this.flowDesc = flowDesc;
	}

}
