package com.tiancom.pas.ticket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;

import com.tiancom.pas.activiti.bean.TaskFlow;
import com.tiancom.pas.activiti.task.NBTaskService;
import com.tiancom.pas.common.framework.ibatis.IBaseDAO;
import com.tiancom.pas.common.result.ResultBean;
import com.tiancom.pas.common.result.ResultCommon;
import com.tiancom.pas.common.result.ResultListData;
import com.tiancom.pas.common.result.ResultMap;
import com.tiancom.pas.common.result.code.NewBeeCode;
import com.tiancom.pas.jz.Jzd;

public class TicketServiceImpl implements TicketService {

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

	
	private void setBxdTaskFlow(List<Jpdd> lst) {
		for (Jpdd bxd : lst) {
			this.setBxdTaskLocalVariable(bxd);
		}
	}

	public List<Map> loadXmList() {
		return this.iBaseDAO.selectListByPara("FinDept_loadXmList", null);
	}

	/**
	 * 设置报销单任务流转中各任务的变量
	 * 
	 * @param bxd
	 */
	private void setBxdTaskLocalVariable(Jpdd bxd) {
		List<TaskFlow> flows = bxd.getTaskFlow();
		if(flows==null) return;
		int size = flows.size();
		for (int i = 0; i < size; i++) {
			TaskFlow flow = flows.get(i);
			HistoricTaskInstance hi = nbTaskService.loadHistoricTaskInstanceList(flow.getTaskId());
			Map<String, Object> taskLocalvariables = hi.getTaskLocalVariables();
			Object approvedObj = taskLocalvariables.get("approved");
			flow.setApproved(approvedObj == null ? true : (Boolean) approvedObj);
			flow.setTaskLocalvariables(taskLocalvariables);
			if(i==size-1) {
				if(flow.getName().contains("申请")) {
					bxd.setBackDd(true);
				}else {
					bxd.setBackDd(false);
				}
			}
		}
		
	}

	public ResultCommon saveTicket(Jpdd dd) {
		try {
			if (dd.getDdbh() == null) {
				this.iBaseDAO.insert("Ticket_insert_wm_jpdd", dd);
			} else {
				this.iBaseDAO.updateByPrimaryKey("Ticket_update_wm_jpdd", dd);
			}
			ResultCommon rc = new ResultCommon(NewBeeCode.SUCCESS);
			return rc;
		} catch (Exception e) {
			e.printStackTrace();
			ResultCommon rc = new ResultCommon(NewBeeCode.ERROR);
			return rc;
		}
	}

	public ResultCommon saveTicketAndStartBTP(Jpdd dd) {
		try {
			if (dd.getDdbh() == null) {
				this.iBaseDAO.insert("Ticket_insert_wm_jpdd", dd);
			} else {
				this.iBaseDAO.updateByPrimaryKey("Ticket_update_wm_jpdd", dd);
			}
			this.startBookingTicketProcess(String.valueOf(dd.getYgbh()), dd.getBmbh(), String.valueOf(dd.getDdbh()));
			ResultCommon rc = new ResultCommon(NewBeeCode.SUCCESS);
			return rc;
		} catch (Exception e) {
			e.printStackTrace();
			ResultCommon rc = new ResultCommon(NewBeeCode.ERROR);
			return rc;
		}
	}

	private void startBookingTicketProcess(String applier, String bmbh, String ddbh) {
		// 流程变量
		Map<String, String> proVars = new HashMap<String, String>();
		proVars.put("applier", applier);
		proVars.put("bmbh", bmbh);
		
		Map localVariables = new HashMap();
		localVariables.putAll(proVars);
		
		proVars.put("ddbh", ddbh);

		String processId = "BTP";
		nbTaskService.startProcessInstanceAndReturn(processId, proVars);
		Task task = nbTaskService.loadLatestTask(processId, applier);
		Map completeVars = new HashMap();
		completeVars.putAll(proVars);
		nbTaskService.completeTask(task.getId(), completeVars, localVariables, applier, null);
	}

	@Override
	public ResultCommon deleteTicketByDdbh(Integer ddbh, String procInstId) {
		try {
			Map map = new HashMap();
			map.put("ddbh", ddbh);
			iBaseDAO.deleteByPrimaryKey("Ticket_Delete_wm_jpdd_By_ddbh", map);
			nbTaskService.deleteProcInstByProcInstId(procInstId);
			ResultCommon rc = new ResultCommon(NewBeeCode.SUCCESS);
			return rc;
		} catch (Exception e) {
			e.printStackTrace();
			ResultCommon rc = new ResultCommon(NewBeeCode.ERROR);
			return rc;
		}
	}

	@Override
	public ResultListData<Jpdd> queryByCondition(String ddbh, String bmid, String ygbh, String dlr, int startRow, int pageSize) {
		ResultListData<Jpdd> rld = null;
		try {
			Map map = new HashMap();
			map.put("ddbh", ddbh);
			map.put("bmid", bmid);
			map.put("ygbh", ygbh);
			map.put("dlr", dlr);
			List<Jpdd> list = iBaseDAO.selectInfoByPara("Ticket_Select_WM_JPDD_ByCondition", map);
			for (Jpdd bxd : list) {
				setBxdTaskLocalVariable(bxd);
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
	public ResultMap queryCountByCondition(String ddbh, String bmid, String ygid, String dlr) {
		ResultMap rm = null;
		try {
			Map map = new HashMap();
			map.put("ddbh", ddbh);
			map.put("bmid", bmid);
			map.put("ygbh", ygid);
			map.put("dlr", dlr);
			Integer cnt = (Integer) iBaseDAO.selectByPrimaryKey("Ticket_Select_WM_JPDD_CountByCondition", map);
			
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
	
	public ResultListData<Jpdd> loadMyAuditingTasks(String ygbh) {
		ResultListData<Jpdd> rld = null;
		try {
			Map map = new HashMap();
			map.put("ygbh", ygbh);
			List<Jpdd> splb = this.iBaseDAO.selectListByPara("Jpdd_selectJpdd_DspLb", map);
			for (Jpdd jzd : splb) {
				setBxdTaskLocalVariable(jzd);
			}
			rld = new ResultListData<Jpdd>(NewBeeCode.SUCCESS);
			rld.setRows(splb);
		}catch(Exception e) {
			e.printStackTrace();	
			rld = new ResultListData<Jpdd>(NewBeeCode.ERROR);			
		}
		return rld;
	}
	

	public ResultListData<Jpdd> loadMyAuditedTasks(String ygbh) {
		ResultListData<Jpdd> rld = null;
		try {
			Map map = new HashMap();
			map.put("ygbh", ygbh);
			List<Jpdd> splb = this.iBaseDAO.selectListByPara("Jpdd_selectJpdd_YspLb", map);
			for (Jpdd jzd : splb) {
				setBxdTaskLocalVariable(jzd);
			}
			rld = new ResultListData<Jpdd>(NewBeeCode.SUCCESS);
			rld.setRows(splb);
		} catch (Exception e) {
			e.printStackTrace();
			rld = new ResultListData<Jpdd>(NewBeeCode.ERROR);
		}
		return rld;
	}
	
	public ResultBean<Jpdd> loadJpddByPk(Integer ddbh){
		ResultBean<Jpdd> rc = new ResultBean(NewBeeCode.SUCCESS);
		try {
			Map map = new HashMap();
			map.put("ddbh", ddbh);
			Jpdd jzd = (Jpdd)iBaseDAO.selectByPrimaryKey("Jpdd_Select_WM_Jpdd_LoadByPK", map);
			setBxdTaskLocalVariable(jzd);
			rc.setRow(jzd);
		}catch(Exception e) {
			e.printStackTrace();
			rc=new ResultBean(NewBeeCode.ERROR);
		}
		return rc;
	}
	
	public List loadLC(String procinstid) {
		Map map = new HashMap();
		map.put("procinstid", procinstid);
		
		
		return this.iBaseDAO.selectListByPara("Jpdd_selectJpdd_LC", map);
	}
}
