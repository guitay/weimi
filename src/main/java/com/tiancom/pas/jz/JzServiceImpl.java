package com.tiancom.pas.jz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.task.Task;

import com.tiancom.pas.activiti.bean.TaskFlow;
import com.tiancom.pas.activiti.task.NBTaskService;
import com.tiancom.pas.common.framework.ibatis.IBaseDAO;
import com.tiancom.pas.common.result.ResultBean;
import com.tiancom.pas.common.result.ResultCommon;
import com.tiancom.pas.common.result.ResultListData;
import com.tiancom.pas.common.result.ResultMap;
import com.tiancom.pas.common.result.code.NewBeeCode;

public class JzServiceImpl implements JzService {

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

	public List<Map> loadXmList() {
		return this.iBaseDAO.selectListByPara("FinDept_loadXmList", null);
	}

	
	public ResultCommon saveJzd(Jzd dd) {
		try {
			if (dd.getJzdh() == null) {
				this.iBaseDAO.insert("Jz_insert_wm_jzd", dd);
			} else {
				this.iBaseDAO.updateByPrimaryKey("Jz_update_wm_jzd", dd);
			}
			ResultCommon rc = new ResultCommon(NewBeeCode.SUCCESS);
			return rc;
		} catch (Exception e) {
			e.printStackTrace();
			ResultCommon rc = new ResultCommon(NewBeeCode.ERROR);
			return rc;
		}
	}

	public ResultCommon saveJzdAndStartCIAP(Jzd dd) {
		try {
			if (dd.getJzdh() == null) {
				this.iBaseDAO.insert("Jz_insert_wm_jzd", dd);
			} else {
				this.iBaseDAO.updateByPrimaryKey("Jz_update_wm_jzd", dd);
			}
			this.startCIAProcess(String.valueOf(dd.getYgbh()), dd.getBmbh(), String.valueOf(dd.getJzdh()));
			ResultCommon rc = new ResultCommon(NewBeeCode.SUCCESS);
			return rc;
		} catch (Exception e) {
			e.printStackTrace();
			ResultCommon rc = new ResultCommon(NewBeeCode.ERROR);
			return rc;
		}
	}

	private void startCIAProcess(String applier, String bmbh, String jzdh) {
		// 流程变量
		Map<String, String> proVars = new HashMap<String, String>();
		proVars.put("bmbh", bmbh);
		
		Map localVariables = new HashMap();
		localVariables.putAll(proVars);
		
		proVars.put("applier", applier);
		proVars.put("jzdh", jzdh);
		boolean isAuditor=isAuditorGroupMember(applier);

		String processId = "CIAP";
		if(isAuditor) {//如果申请人属于auditor组，则为中心总经理角色
				processId="MCIAP";
		}
		nbTaskService.startProcessInstanceAndReturn(processId, proVars);
		Task task = nbTaskService.loadLatestTask(processId, applier);
		Map completeVars = new HashMap();
		completeVars.putAll(proVars);
		nbTaskService.completeTask(task.getId(), completeVars, localVariables, applier, null);
	}

	/**
	 * 是否属于中心经理用户组
	 * @param ygbh
	 * @return
	 */
	private boolean isAuditorGroupMember(String ygbh) {
		List<Group> grps = this.nbTaskService.loadGroupsByMember(ygbh);
		for(Group grp :grps) {
			if(grp.getId().endsWith("auditor")) {
				return true;
			}
		}
		return false;
	}
	@Override
	public ResultCommon deleteJzdByJzdh(Integer jzdh, String procInstId) {
		try {
			Map map = new HashMap();
			map.put("jzdh", jzdh);
			iBaseDAO.deleteByPrimaryKey("Jz_Delete_wm_jzd_By_jzdh", map);
			nbTaskService.deleteProcInstByProcInstId(procInstId);
			ResultCommon rc = new ResultCommon(NewBeeCode.SUCCESS);
			return rc;
		} catch (Exception e) {
			e.printStackTrace();
			ResultCommon rc = new ResultCommon(NewBeeCode.ERROR);
			return rc;
		}
	}
	
	public ResultBean<Jzd> loadJzdByPk(Integer jzdh){
		ResultBean<Jzd> rc = new ResultBean(NewBeeCode.SUCCESS);
		try {
			Map map = new HashMap();
			map.put("jzdh", jzdh);
			Jzd jzd = (Jzd)iBaseDAO.selectByPrimaryKey("Jz_Select_WM_JZD_LoadByPK", map);
			setJzdTaskLocalVariable(jzd);
			rc.setRow(jzd);
		}catch(Exception e) {
			e.printStackTrace();
			rc=new ResultBean(NewBeeCode.ERROR);
		}
		return rc;
	}

	public ResultListData<Jzd> queryByCondition(String jzdh, String xmbh, String bmid, String ygbh) {
		ResultListData<Jzd> rld = null;
		try {
			Map map = new HashMap();
			map.put("jzdh", jzdh);
			map.put("xmbh", xmbh);
			map.put("bmid", bmid);
			map.put("ygbh", ygbh);
			List<Jzd> list = iBaseDAO.selectListByPara("Jz_Select_WM_JZD_ByCondition", map);
			for(Jzd jzd : list) {
				setJzdTaskLocalVariable(jzd);
			}
			rld = new ResultListData(NewBeeCode.SUCCESS);
			rld.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
			rld = new ResultListData(NewBeeCode.ERROR);
		}
		return rld;
	}
	
	/**
	 * 设置报销单任务流转中各任务的变量
	 * @param jzd
	 */
	private void setJzdTaskLocalVariable(Jzd jzd) {
		List<TaskFlow> flows = jzd.getTaskFlow();
		if (flows == null) return;
		int size = flows.size();
		for(int i=0;i<size;i++) {
			TaskFlow flow = flows.get(i);
			HistoricTaskInstance hi = nbTaskService.loadHistoricTaskInstanceList(flow.getTaskId());
			Map<String,Object> taskLocalvariables = hi.getTaskLocalVariables();
			Object approvedObj = taskLocalvariables.get("approved");
			flow.setApproved(approvedObj==null ? true:(Boolean)approvedObj);
			flow.setTaskLocalvariables(taskLocalvariables);
			if(i==size-1) {
				if(flow.getName().contains("申请")) {
					jzd.setBackJzd(true);
				}else {
					jzd.setBackJzd(false);
				}
			}
		}
	}

	
	public ResultListData<Jzd> queryByCondition(String jzdh, String xmbh, String bmid, String ygbh,String dlr, int startRow, int pageSize) {
		ResultListData<Jzd> rld = null;
		
		try {
			Map map = new HashMap();
			map.put("jzdh", jzdh);
			map.put("xmbh", xmbh);
			map.put("bmid", bmid);
			map.put("ygbh", ygbh);
			map.put("dlr", dlr);
			List<Jzd> list = iBaseDAO.selectPageInfoByPara("Jz_Select_WM_JZD_ByCondition", map, startRow,
					pageSize);
			for(Jzd jzd : list) {
				setJzdTaskLocalVariable(jzd);
			}
			rld = new ResultListData(NewBeeCode.SUCCESS);
			rld.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
			rld = new ResultListData(NewBeeCode.ERROR);
		}
		return rld;
	}
	

	@Override
	public ResultMap queryCountByCondition(String jzdh, String xmid, String bmid, String ygbh, String dlr) {
		ResultMap rm = null;
		try {
			Map map = new HashMap();
			map.put("jzdh", jzdh);
			map.put("xmid", xmid);
			map.put("bmid", bmid);
			map.put("ygbh", ygbh);
			map.put("dlr", dlr);
			Integer cnt = (Integer) iBaseDAO.selectByPrimaryKey("Jz_Select_WM_JZD_CountByCondition", map);
			
			rm = new ResultMap(NewBeeCode.SUCCESS);
			Map rs = new HashMap();
			rs.put("count", cnt);
			rm.setRows(rs);
		} catch (Exception e) {
			e.printStackTrace();
			rm = new ResultMap(NewBeeCode.ERROR);
		}
		return rm;
	}
	
	
	public ResultListData<Jzd> loadMyAuditingTasks(String ygbh) {
		ResultListData<Jzd> rld = null;
		try {
			Map map = new HashMap();
			map.put("ygbh", ygbh);
			List<Jzd> splb = this.iBaseDAO.selectListByPara("Jz_selectJzd_DspLb", map);
			for (Jzd jzd : splb) {
				setJzdTaskLocalVariable(jzd);
			}
			rld = new ResultListData<Jzd>(NewBeeCode.SUCCESS);
			rld.setRows(splb);
		}catch(Exception e) {
			e.printStackTrace();	
			rld = new ResultListData<Jzd>(NewBeeCode.ERROR);			
		}
		return rld;
	}
	

	public ResultListData<Jzd> loadMyAuditedTasks(String ygbh) {
		ResultListData<Jzd> rld = null;
		try {
			Map map = new HashMap();
			map.put("ygbh", ygbh);
			List<Jzd> splb = this.iBaseDAO.selectListByPara("Jz_selectJzd_YspLb", map);
			for (Jzd jzd : splb) {
				setJzdTaskLocalVariable(jzd);
			}
			rld = new ResultListData<Jzd>(NewBeeCode.SUCCESS);
			rld.setRows(splb);
		} catch (Exception e) {
			e.printStackTrace();
			rld = new ResultListData<Jzd>(NewBeeCode.ERROR);
		}
		return rld;
	}
	

	public boolean isSzqrTask(String taskId) {
		if (taskId == null)
			return false;
		Task task = this.nbTaskService.loadTaskDetailByTaskId(taskId);
		if (task == null)
			return false;
		else
			return task.getTaskDefinitionKey().equals("applier-confirm");
	}

	@Override
	public List loadLC(String procinstid) {
		Map map = new HashMap();
		map.put("procinstid", procinstid);
		
		
		return this.iBaseDAO.selectListByPara("Jz_selectJzd_LC", map);
	}
}
