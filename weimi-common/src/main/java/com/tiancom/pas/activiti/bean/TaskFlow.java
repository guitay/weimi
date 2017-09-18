package com.tiancom.pas.activiti.bean;

import java.util.Date;
import java.util.Map;

public class TaskFlow {
	private String taskId;
	private String procInstId;
	private String name;
	private String assignee;
	private Date startTime;
	private Date claimTime;
	private Date endTime;
	private Map<String,Object> taskLocalvariables;
	private Boolean approved;
	
	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Map<String, Object> getTaskLocalvariables() {
		return taskLocalvariables;
	}

	public void setTaskLocalvariables(Map<String, Object> taskLocalvariables) {
		this.taskLocalvariables = taskLocalvariables;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getClaimTime() {
		return claimTime;
	}

	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
