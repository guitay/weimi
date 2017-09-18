package com.tiancom.pas.mobilew;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;

import com.tiancom.pas.activiti.bean.TaskFlow;
import com.tiancom.pas.activiti.task.NBTaskService;
import com.tiancom.pas.common.framework.ibatis.IBaseDAO;

public class MobilewServiceImpl implements MobilewService {

	private IBaseDAO ibaseDAO;

	private NBTaskService taskService;

	public NBTaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(NBTaskService taskService) {
		this.taskService = taskService;
	}

	public IBaseDAO getIbaseDAO() {
		return ibaseDAO;
	}

	public void setIbaseDAO(IBaseDAO ibaseDAO) {
		this.ibaseDAO = ibaseDAO;
	}

	public String zxbd(Map map) {
		this.ibaseDAO.deleteByPrimaryKey("Mobilew_zxbd", map);
		return "注销成功";
	}

	@Override
	public List<Bxd> selectBxdLb(Map map) {
		List<Bxd> bxdList = this.ibaseDAO.selectListByPara("Mobilew_selectBxdLb", map);
		for(Bxd bxd : bxdList) {
			setBxdTaskLocalVariable(bxd);
		}
		return bxdList;
	}

	public List<Bxd> selectBxdLb_btg(Map map) {
		List<Bxd> list = this.ibaseDAO.selectListByPara("Mobilew_selectBxdLb_btg", map);
		for (Bxd m : list) {
			String taskId = m.getTaskId();
			m.setXgyj((String) taskService.getTaskVariables(taskId).get("comment"));
			
			setBxdTaskLocalVariable(m);
		}
		return list;
	}

	public Bxd selectBxdxq(Map map) {
		Bxd bxd = (Bxd)this.ibaseDAO.selectByPrimaryKey("Mobilew_selectBxdxq", map);
		setBxdTaskLocalVariable(bxd);
		return bxd;
	}

	public List selectBxdZdxq(Map map) {
		return this.ibaseDAO.selectListByPara("Mobilew_selectBxdZdxq", map);
	}

	public List<Zdxq> selectBxdZdxqHz(Map map) {
		return this.ibaseDAO.selectListByPara("Mobilew_selectBxdZdxqHz", map);
	}

	public void deleteBxd(Map map) {
		this.ibaseDAO.deleteByPrimaryKey("Mobilew_deleteBxd", map);
		this.ibaseDAO.updateByPrimaryKey("Mobilew_updateZd", map);
		// 删除流程实例
		Object taskIdObj = map.get("taskId");
		String taskId = taskIdObj == null ? null : (String) taskIdObj;
		if (taskId != null) {
			this.taskService.deleteProcInstByTaskId(taskId);
		}
	}

	public void updateBxd(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilew_updateBxd", map);
	}

	public List selectBxspLb(Map map) {
		return this.ibaseDAO.selectListByPara("Mobilew_selectBxspLb", map);
	}

	public void TyBxd(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilew_TyBxd", map);
	}

	public void BtyBxd(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilew_BtyBxd", map);
	}

	public void updateBxdZt(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilew_updateBxdZt", map);
	}

	public String getGw(Map map) {
		return this.ibaseDAO.selectByPrimaryKey("Mobilew_getGw", map).toString();

	}

	public void Szqr(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilew_Szqr", map);
	}

	public List<Bxd> loadMyAuditingTasks(String ygbh) {
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		List<Bxd> splb = this.ibaseDAO.selectListByPara("Mobilew_selectBxspLb", map);
		for (Bxd bxd : splb) {
			setBxdTaskLocalVariable(bxd);
		}
		return splb;
	}
	

	public List<Bxd> loadMyAuditedTasks(String ygbh){
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		List<Bxd> splb = this.ibaseDAO.selectListByPara("Mobilew_selectBxYspLb", map);
		for (Bxd bxd : splb) {
			setBxdTaskLocalVariable(bxd);
		}
		return splb;
	}

	/**
	 * 设置报销单任务流转中各任务的变量
	 * @param bxd
	 */
	private void setBxdTaskLocalVariable(Bxd bxd) {
		List<TaskFlow> flows = bxd.getTaskFlow();
		int size = flows.size();
		for(int i=0;i<size;i++) {
			TaskFlow flow = flows.get(i);
			HistoricTaskInstance hi = taskService.loadHistoricTaskInstanceList(flow.getTaskId());
			Map<String,Object> taskLocalvariables = hi.getTaskLocalVariables();
			Object approvedObj = taskLocalvariables.get("approved");
			flow.setApproved(approvedObj==null ? true:(Boolean)approvedObj);
			flow.setTaskLocalvariables(taskLocalvariables);

			if(i==size-1) {
				if(flow.getName().contains("申请")) {
					bxd.setBackJzd(true);
				}else {
					bxd.setBackJzd(false);
				}
			}
		}
	}

	public boolean isSzqrTask(String taskId) {
		if (taskId == null)
			return false;
		Task task = this.taskService.loadTaskDetailByTaskId(taskId);
		if (task == null)
			return false;
		else
			return task.getTaskDefinitionKey().equals("applier-confirm");
	}
}
