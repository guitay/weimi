package com.tiancom.pas.mobilezd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.identity.Group;
import org.activiti.engine.task.Task;

import com.tiancom.pas.activiti.task.NBTaskService;
import com.tiancom.pas.common.framework.ibatis.IBaseDAO;
import com.tiancom.pas.common.proc.entity.QueryUtilPojo;
import com.tiancom.pas.common.proc.service.QueryUtilService;

public class MobilezdServiceImpl implements MobilezdService {

	private IBaseDAO ibaseDAO;
	
	private NBTaskService taskService;
	
	
	private QueryUtilService utilServ;
	

	public QueryUtilService getUtilServ() {
		return utilServ;
	}

	public void setUtilServ(QueryUtilService utilServ) {
		this.utilServ = utilServ;
	}

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

	
	public void insertZd(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilezd_insertZd", map);
	}
	
	public List selectZdXm(Map map){
		
		return this.ibaseDAO.selectInfoByPara("Mobilezd_selectZdXm", map);
	}
	
	public List selectZdHeader(Map map){
		
		return this.ibaseDAO.selectInfoByPara("Mobilezd_selectZdHeader", map);
	}
	
	public List selectZdMx(Map map){
		
		return this.ibaseDAO.selectInfoByPara("Mobilezd_selectZdMx", map);
	}
	
	public List selectBm(Map map){
		
		return this.ibaseDAO.selectInfoByPara("Mobilezd_selectBm", map);
	}

	public List selectZdMxhz(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilezd_selectZdMxhz", map);
	}
	
	public void insertBxd(Map map) {
		map.put("bxdh", null);
		this.ibaseDAO.insert("Mobilezd_insertBxd", map);
		map.put("bxdh", String.valueOf(map.get("bxdh")));
		this.ibaseDAO.updateByPrimaryKey("Mobilezd_updateZdBxdh", map);
		starProcess(map);//启动审批流程
	}

	private void starProcess(Map<String, String> map) {
		String ygbh = map.get("ygbh");
		String bmId = map.get("bxbm");
		QueryUtilPojo rootJg = utilServ.selectByKey2(Integer.valueOf(bmId));
		String bmbh = rootJg.getBmbh();
		String bxje = map.get("bxje");
		boolean isAuditor=isAuditorGroupMember(ygbh);
		
		//流程变量
		Map<String, String> proVars = new HashMap<String, String>();
		proVars.put("applier", ygbh);
		proVars.put("bmbh", bmbh);
		
		Map localVariables = new HashMap();
		localVariables.putAll(proVars);
		
		proVars.put("bxdh", String.valueOf(map.get("bxdh")));
		
		//判断走哪个流程CEP\MCEP\CCEP
		String processId = "";
		if(isAuditor) {//如果申请人属于auditor组，则为中心总经理角色
			if(bmbh.equals("0000")) {//如果部门编号为公司，则属于公司费用，走CCEP流程
				proVars.put("bxje", bxje);
				processId="CCEP";
			}else {//部门费用，走MCEP流程
				processId="MCEP";
			}
		}else {//普通员工走CEP流程
			processId="CEP";
		}
		
		taskService.startProcessInstanceAndReturn(processId, proVars);
		Task task = taskService.loadLatestTask(processId, ygbh);
		Map completeVars = new HashMap();
		completeVars.putAll(proVars);
		taskService.completeTask(task.getId(), completeVars, localVariables, ygbh, null);
	}

	/**
	 * 是否属于中心经理用户组
	 * @param ygbh
	 * @return
	 */
	private boolean isAuditorGroupMember(String ygbh) {
		List<Group> grps = this.taskService.loadGroupsByMember(ygbh);
		for(Group grp :grps) {
			if(grp.getId().endsWith("auditor")) {
				return true;
			}
		}
		return false;
	}
	
	public List selectZdByZdbh(Map map) {
		return ibaseDAO.selectInfoByPara("Mobilezd_selectZdByZdbh", map);
	}
	
	public void updateZd(Map map) {
		this.ibaseDAO.updateByPrimaryKey("Mobilezd_updateZd", map);
	}
	
	public void deleteZd(Map map){
		this.ibaseDAO.deleteByPrimaryKey("Mobilezd_deleteZd", map);
		
	}
	
}
