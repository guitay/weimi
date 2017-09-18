package com.tiancom.pas.activiti.task;

import java.util.List;
import java.util.Map;

import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Component;

import com.tiancom.pas.activiti.bean.NBTask;
import com.tiancom.pas.common.result.ResultListData;

@Component
public interface NBTaskService {
	
	public static final String KEY = "Task.TaskService";

	public ProcessInstance startProcessInstanceAndReturn(String processDefinitionKey,  Map<String, String> variables);
	
//	public void setTaskLocalVariables(String taskId,Map<String,String> variables);
	/**
	 * 待办列表
	 * @param processDefinitionKey TODO
	 * @param record
	 * @param jlrdh
	 *            登陆用户,为了添加修改日志需要.
	 */
	public ResultListData<NBTask> loadTaskTodoList(String processDefinitionKey, String hydh);
	
	/**
	 * 取得最新分配的任务
	 * @param processDefinitionKey 流程定义KEY
	 * @param ygbh 员工编号
	 * @return
	 */
	public Task loadLatestTask(String processDefinitionKey, String ygbh);
	
	/**
	 * 根据TaskId取得Task详情
	 * @param taskId
	 * @return
	 */
	public Task loadTaskDetailByTaskId(String taskId) ;
	
	/**
	 * 按用户名，取得用户所在组
	 * @param ygbh
	 * @return
	 */
	public List<Group> loadGroupsByMember(String ygbh);
	
	public List<HistoricProcessInstance> loadHistoricProcessInstanceList(String applier, Integer jxdxdh, int startRow,
			int pageSize);
	
	public HistoricTaskInstance loadHistoricTaskInstanceList(String taskId);
	
	/**
	 * 完成任务
	 * @param taskId 任务ID
	 * @param variables 流程变量(合局)
	 * @param localVariables 任务变量 
	 * @param ygbh 签收员工编号
	 * @param comment 任务备注
	 */
	public void completeTask(String taskId, Map<String, Object> variables, Map<String, Object> localVariables, String ygbh, String comment);

	/**
	 * 按任务ID获取任务Form数据
	 * @param taskId
	 * @return
	 */
	public TaskFormData getTaskFormDataByTaskId(String taskId);
	
	public Map getTaskVariables(String taskId);
	
	public Object getRenderedTaskFormByTaskId(String taskId);
	
	/**
	 * 根据任务ID删除任务相关流程实例
	 * @param taskId
	 */
	public void deleteProcInstByTaskId(String taskId);
	
	/**
	 * 根据流程ID删除相关流程实例
	 * @param taskId
	 */	
	public void deleteProcInstByProcInstId(String procInstId);
	
	
}
