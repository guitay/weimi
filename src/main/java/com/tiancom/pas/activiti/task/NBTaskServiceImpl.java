package com.tiancom.pas.activiti.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.identity.Group;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;

import com.tiancom.pas.activiti.bean.NBTask;
import com.tiancom.pas.common.result.ResultListData;
import com.tiancom.pas.common.result.code.NewBeeCode;

public class NBTaskServiceImpl implements NBTaskService {

	private RuntimeService runtimeService;

	private ProcessEngine processEngine;

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public ResultListData<NBTask> loadTaskTodoList(String processDefinitionKey, String ygbh) {
		ResultListData<NBTask> rld = null;
		try {
			TaskService taskService = processEngine.getTaskService();
			
			List<Task> tasks = processEngine.getTaskService()// 与任务相关的Service
					.createTaskQuery()// 创建一个任务查询对象
					.taskAssignee(ygbh).active().list();
			
			List<Group> groups = loadGroupsByMember(ygbh);
			for (Group group : groups) {
				List<Task> grpTaskList = taskService.createTaskQuery().taskCandidateGroup(group.getId()).list();
				tasks.addAll(grpTaskList);
			}
			List<NBTask> nbtasks = new ArrayList<NBTask>();
			
			for(Task t : tasks) {
				NBTask nt = new NBTask();
				String procDefId = t.getProcessDefinitionId();
				String procName = processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult().getName();
				BeanUtils.copyProperties(nt, t);
				nt.setProcessName(procName);
				nbtasks.add(nt);
			}
			rld = new ResultListData(NewBeeCode.SUCCESS);
			rld.setRows(nbtasks);
		}catch(Exception e) {
			e.printStackTrace();
			rld = new ResultListData(NewBeeCode.ERROR);
		}

		return rld;
	}

	public Task loadLatestTask(String processDefinitionKey, String ygbh) {
		List<Task> tasks = processEngine.getTaskService().createTaskQuery()//.taskDefinitionKey(processDefinitionKey)
				.taskAssignee(ygbh).orderByTaskCreateTime().desc().list();
		Task task = tasks.get(0);
		return task;
	}
	
	public Task loadTaskDetailByTaskId(String taskId) {
		Task task = processEngine.getTaskService().createTaskQuery()//.taskDefinitionKey(processDefinitionKey)
				.taskId(taskId).singleResult();
		
		return task;
	}
	
	public List<Group> loadGroupsByMember(String ygbh) {
		List<Group> groups = processEngine.getIdentityService().createGroupQuery().groupMember(ygbh).list();
		return groups;
	}

	public List<HistoricProcessInstance> loadHistoricProcessInstanceList(String applier, Integer jxdxdh, int startRow,
			int pageSize) {
		HistoricProcessInstanceQuery createHistoricProcessInstanceQuery = processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery();
		HistoricProcessInstanceQuery query = createHistoricProcessInstanceQuery.finished();
		if (applier != null) {
			query = query.variableValueEquals("applier", applier);
		}
		if (jxdxdh != null) {
			query = query.variableValueEquals("jxdxdh", jxdxdh);
		}
		List<HistoricProcessInstance> list = query.listPage(startRow, pageSize);
		return list;
	}
	
	public List<HistoricProcessInstance> loadHistoricTaskInstanceListByCondition(String ygbh,String applier, Integer jxdxdh, int startRow,
			int pageSize) {
		HistoricProcessInstanceQuery hpQuery = processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery();
		HistoricTaskInstanceQuery tiQuery = processEngine.getHistoryService().createHistoricTaskInstanceQuery();
		tiQuery.taskAssignee(ygbh).taskDefinitionKeyLike("audit");
		
		return null;
//		return history;
	}
	

	public HistoricTaskInstance loadHistoricTaskInstanceList(String taskId) {
		HistoricTaskInstanceQuery taskInstQuery = processEngine.getHistoryService().createHistoricTaskInstanceQuery();
		HistoricTaskInstance history = taskInstQuery.taskId(taskId).includeTaskLocalVariables().singleResult();

		return history;
	}
	

	public List<HistoricProcessInstance> loadHistoricActivityInstanceList(String applier, Integer jxdxdh, int startRow,
			int pageSize) {
//		HistoricActivityInstanceQuery haiq = processEngine.getHistoryService().createHistoricActivityInstanceQuery();
//		haiq.activityId(arg0)
		
//		if (applier != null) {
//			query = query.variableValueEquals("applier", applier);
//		}
//		if (jxdxdh != null) {
//			query = query.variableValueEquals("jxdxdh", jxdxdh);
//		}
//		List<HistoricProcessInstance> list = query.listPage(startRow, pageSize);
//		return list;
		return null;
	}

	public ProcessInstance startProcessInstanceAndReturn(String processDefinitionKey, Map<String, String> variables) {
		processEngine.getIdentityService().setAuthenticatedUserId((String) variables.get("applier"));
		//如果有多版本流程定义，则使用最新版本
		ProcessDefinition pd = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey).orderByProcessDefinitionVersion().desc().list().get(0);
		ProcessInstance pi = processEngine.getFormService().submitStartFormData(pd.getId(), variables);

		System.out.println("流程实例ID：" + pi.getId());// 流程实例ID：101
		System.out.println("流程实例ID：" + pi.getProcessInstanceId());// 流程实例ID：101
		System.out.println("流程实例ID:" + pi.getProcessDefinitionId());// myMyHelloWorld:1:4

		return pi;
	}

	public Map getTaskVariables(String taskId) {
		Map map = processEngine.getTaskService().getVariables(taskId);
		return map;
	}
	
//	public void setTaskLocalVariables(String taskId,Map<String,String> variables) {
//		processEngine.getTaskService().setVariablesLocal(taskId, variables);
//	}

	public Object getRenderedTaskFormByTaskId(String taskId) {
		return processEngine.getFormService().getRenderedTaskForm(taskId);
	}

	public TaskFormData getTaskFormDataByTaskId(String taskId) {
		return processEngine.getFormService().getTaskFormData(taskId);
	}

	/** 完成任务 */
	public void completeTask(String taskId, Map<String, Object> variables, Map<String, Object> localVariables, String ygbh, String comment) {
		TaskService taskService = processEngine.getTaskService();
		taskService.claim(taskId, ygbh);//签收

		Map taskVariables = processEngine.getTaskService().getVariables(taskId);
		if(variables!=null) {
			taskVariables.putAll(variables);
		}
		if(localVariables!=null) {
			taskVariables.putAll(localVariables);//将任务变量加入全局变量，否则下一活动节点，可能引用了该任务变量
		}
		taskService.setVariables(taskId, taskVariables);//设置流程级别变量;必须先设置全局变量，否则当前任务变量与合局变量同名的，在历史中将被覆盖
		if(localVariables!=null) {
			taskService.setVariablesLocal(taskId, localVariables);//设置Task级别变量
		}
		
		if(comment!=null&&!comment.equals("")) {
			taskService.addComment(taskId, null,comment);//设置任务备注
		}
		
		taskService.complete(taskId);//完成任务

		System.out.println("完成任务：" + taskId);
	}

	@Override
	public void deleteProcInstByTaskId(String taskId) {
		Task task = this.loadTaskDetailByTaskId(taskId);
		this.processEngine.getRuntimeService().deleteProcessInstance(task.getProcessInstanceId(), null);//删除运行环境
		this.processEngine.getHistoryService().deleteHistoricProcessInstance(task.getProcessInstanceId());//删除历史
	}
	

	@Override
	public void deleteProcInstByProcInstId(String procInstId) {
		this.processEngine.getRuntimeService().deleteProcessInstance(procInstId, null);//删除运行环境
		this.processEngine.getHistoryService().deleteHistoricProcessInstance(procInstId);//删除历史
	}

	
}
