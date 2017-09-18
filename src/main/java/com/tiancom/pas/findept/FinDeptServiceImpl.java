package com.tiancom.pas.findept;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;

import com.tiancom.pas.activiti.bean.TaskFlow;
import com.tiancom.pas.activiti.task.NBTaskService;
import com.tiancom.pas.common.framework.ibatis.IBaseDAO;

public class FinDeptServiceImpl implements FinDeptService {

	private IBaseDAO iBaseDAO;

	private NBTaskService nbTaskService;

	public NBTaskService getNbTaskService() {
		return nbTaskService;
	}

	public void setNbTaskService(NBTaskService nbTaskService) {
		this.nbTaskService = nbTaskService;
	}

	public IBaseDAO getiBaseDAO() {
		return iBaseDAO;
	}

	public void setiBaseDAO(IBaseDAO iBaseDAO) {
		this.iBaseDAO = iBaseDAO;
	}

	/** 完成任务 */
	public void completeTask(String taskId, Map variables, String ygbh) {
		this.nbTaskService.completeTask(taskId, null, variables, ygbh, null);
	}

	public List<Bxd> loadAuditingTaskList(Map params) {
		int czzt = Integer.valueOf((String)params.get("czzt")).intValue();
		List<Bxd> lst = null;
		if(czzt==1) {
			lst = (List) this.iBaseDAO.selectInfoByPara("FinDept_loadAuditingTaskList", params);
		}else {
			lst = (List) this.iBaseDAO.selectInfoByPara("FinDept_selectBxYspLb", params);
		}
		setBxdTaskFlow(lst);
		return lst;
	}

	public List<Bxd> loadCashierTaskList(Map params) {
		int czzt = Integer.valueOf((String)params.get("czzt")).intValue();
		List<Bxd> lst = null;
		if(czzt==1) {
			lst = (List) this.iBaseDAO.selectInfoByPara("FinDept_loadCashierTaskList", params);
		}else {
			lst = (List) this.iBaseDAO.selectInfoByPara("FinDept_selectBxYspLb", params);
		}
		setBxdTaskFlow(lst);
		
		return lst;
	}

	public List<Bxd> loadAssistantTaskList(Map params) {
		int czzt = Integer.valueOf((String)params.get("czzt")).intValue();
		List<Bxd> lst = null;
		if(czzt==1) {
			lst = (List) this.iBaseDAO.selectInfoByPara("Assistant_loadAssistantTaskList", params);
		}else {
			lst = (List) this.iBaseDAO.selectInfoByPara("FinDept_selectBxYspLb", params);
		}
		setBxdTaskFlow(lst);
		return lst;
	}

	public int loadWmZdCount(Map map) {
		Integer count = (Integer) this.iBaseDAO.selectByPrimaryKey("FinDept_selectWmZdCount", map);
		return count.intValue();
	}
	public List<WmZd> loadWmZdList(Map map, int startRow, int pageSize) {
		List<WmZd> list = (List) this.iBaseDAO.selectPageInfoByPara("FinDept_selectWmZdList", map, startRow, pageSize);
		return list;
	}

	private void setBxdTaskFlow(List<Bxd> lst) {
		for (Bxd bxd : lst) {
			this.setBxdTaskLocalVariable(bxd);
		}
	}

	public List<Map> loadXmList() {
		return this.iBaseDAO.selectListByPara("FinDept_loadXmList", null);
	}

	@Override
	public List<TaskFlow> loadBxdTaskFlow(Integer bxdh) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bxd loadBxdByBxdh(String bxdh) {

//		return this.ibaseDao.selectListByPara("Mobilew_selectBxdxq", map);
	
		return null;
	}

	/**
	 * 设置报销单任务流转中各任务的变量
	 * 
	 * @param bxd
	 */
	private void setBxdTaskLocalVariable(Bxd bxd) {
		List<TaskFlow> flows = bxd.getTaskFlow();
		String flowDesc = "";
		int size = flows.size();
		for (int i=0;i<size;i++) {
			TaskFlow flow = flows.get(i);
			HistoricTaskInstance hi = nbTaskService.loadHistoricTaskInstanceList(flow.getTaskId());
			Map<String, Object> taskLocalvariables = hi.getTaskLocalVariables();
			Object approvedObj = taskLocalvariables.get("approved");
			flow.setApproved(approvedObj == null ? true : (Boolean) approvedObj);
			flow.setTaskLocalvariables(taskLocalvariables);
			if(flowDesc.equals("")) {
				if(i!=size-1) {//不是最后一个就加粗
					flowDesc+= "<b>"+flow.getName()+"</b>";
				}else
					flowDesc+= flow.getName();
			}else {
				if(i!=size-1) {
					flowDesc+="-><b>"+flow.getName()+"</b>";
				}else {
					flowDesc+="->"+flow.getName();
				}
			}
		}
		bxd.setFlowDesc(flowDesc);
	}

	public List<Bxd> loadTaskList(Map params) {
			
		return (List) this.iBaseDAO.selectInfoByPara("FinDept_loadTaskList", params);
	}
	
	public List loadZD(String bxdh) {
		Map map = new HashMap();
		map.put("bxdh", bxdh);
		
		
		return this.iBaseDAO.selectListByPara("FinDept_selectBxd_ZD", map);
	}
	
	public List loadLC(String procinstid) {
		Map map = new HashMap();
		map.put("procinstid", procinstid);
		
		
		return this.iBaseDAO.selectListByPara("FinDept_selectBxd_LC", map);
	}
}
