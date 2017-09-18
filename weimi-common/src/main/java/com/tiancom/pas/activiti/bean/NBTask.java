package com.tiancom.pas.activiti.bean;

import java.util.Date;

import com.tiancom.pas.common.result.Model;

public class NBTask extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5970875675735604900L;
	private String id;
	private String processInstanceId;
	private String name;
	private String processName;
	private Date createTime;

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
