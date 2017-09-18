package com.tiancom.pas.mobilew;

import java.math.BigDecimal;
import java.util.List;

import com.tiancom.pas.activiti.bean.TaskFlow;

public class Bxd {
	private Integer bxdh;
	private String xmjc;
	private Integer bxbm;
	private String bmmc;
	private String xm;
	private Integer qsrq;
	private Integer jsrq;
	private BigDecimal bxje;
	private BigDecimal bxze;
	private String bxzt;
	private String xgyj;// 修改意见
	private String taskId;
	private String procInstId;
	private Integer ccts;
	private List<TaskFlow> taskFlow;// 流转流程

	private boolean backJzd;// 是否为被退回的借支单

	public BigDecimal getBxje() {
		return bxje;
	}

	public void setBxje(BigDecimal bxje) {
		this.bxje = bxje;
	}

	public Integer getBxbm() {
		return bxbm;
	}

	public void setBxbm(Integer bxbm) {
		this.bxbm = bxbm;
	}

	public Integer getCcts() {
		return ccts;
	}

	public void setCcts(Integer ccts) {
		this.ccts = ccts;
	}

	public boolean isBackJzd() {
		return backJzd;
	}

	public void setBackJzd(boolean backJzd) {
		this.backJzd = backJzd;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public Integer getBxdh() {
		return bxdh;
	}

	public void setBxdh(Integer bxdh) {
		this.bxdh = bxdh;
	}

	public String getXmjc() {
		return xmjc;
	}

	public void setXmjc(String xmjc) {
		this.xmjc = xmjc;
	}

	public Integer getQsrq() {
		return qsrq;
	}

	public void setQsrq(Integer qsrq) {
		this.qsrq = qsrq;
	}

	public Integer getJsrq() {
		return jsrq;
	}

	public void setJsrq(Integer jsrq) {
		this.jsrq = jsrq;
	}

	public BigDecimal getBxze() {
		return bxze;
	}

	public void setBxze(BigDecimal bxze) {
		this.bxze = bxze;
	}

	public String getBxzt() {
		return bxzt;
	}

	public void setBxzt(String bxzt) {
		this.bxzt = bxzt;
	}

	public String getXgyj() {
		return xgyj;
	}

	public void setXgyj(String xgyj) {
		this.xgyj = xgyj;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public List<TaskFlow> getTaskFlow() {
		return taskFlow;
	}

	public void setTaskFlow(List<TaskFlow> taskFlow) {
		this.taskFlow = taskFlow;
	}

}
