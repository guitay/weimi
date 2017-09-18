package com.tiancom.pas.deploy.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.task.Task;

import com.tiancom.pas.activiti.task.NBTaskService;
import com.tiancom.pas.common.framework.ibatis.IBaseDAO;

public class InitUpdateServiceImpl implements InitUpdateService {

	private IBaseDAO ibaseDao;
	
	private NBTaskService taskService;

	public NBTaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(NBTaskService taskService) {
		this.taskService = taskService;
	}

	public IBaseDAO getIbaseDao() {
		return ibaseDao;
	}

	public void setIbaseDao(IBaseDAO ibaseDao) {
		this.ibaseDao = ibaseDao;
	}

	@Override
	public void rebootProcessBxd() {
		List<Bxd> list = (List) ibaseDao.selectListByPara("Init_selectRebootProcessBxd", null);
		for (Bxd bxd : list) {
			this.starProcess(bxd.getYgbh(), bxd.getBxdh(), bxd.getBmbh(), bxd.getBxje().toString(),bxd.getBxzt());
		}
		
		//cep待审批列表
		List<Bxd> cepDsplist = (List) ibaseDao.selectListByPara("Init_selectBxdDsp_CEP_PROC_List", null);
		for (Bxd bxd : cepDsplist) {
			Map variables = new HashMap();
			variables.put("comment", "");
			variables.put("approved", new Boolean(true));
			this.taskService.completeTask(bxd.getTaskId(), null, variables, bxd.getSpr_ygbh(), null);
		}
	}

	private void starProcess(String ygbh,Integer bxdh,String bmbh,String bxje,String bxzt) {
		boolean isAuditor = isAuditorGroupMember(ygbh);
		// 流程变量
		Map<String, String> proVars = new HashMap<String, String>();
		proVars.put("applier", ygbh);
		proVars.put("bmbh", bmbh);
		proVars.put("bxdh", String.valueOf(bxdh));

		// 判断走哪个流程CEP\MCEP\CCEP
		String processId = "";
		if (isAuditor) {// 如果申请人属于auditor组，则为中心总经理角色
			if (bmbh.equals("0000")) {// 如果部门编号为公司，则属于公司费用，走CCEP流程
				proVars.put("bxje", bxje);
				processId = "CCEP";
			} else {// 部门费用，走MCEP流程
				processId = "MCEP";
			}
		} else {// 普通员工走CEP流程
			processId = "CEP";
		}

		taskService.startProcessInstanceAndReturn(processId, proVars);
		Task task = taskService.loadLatestTask(processId, ygbh);
		Map completeVars = new HashMap();
		completeVars.putAll(proVars);
		taskService.completeTask(task.getId(), completeVars, null, ygbh, null);
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
	
	

	public void deleteBxd(String bxdh,String procInstId) {
		Map map = new HashMap();
		map.put("bxdh", bxdh);
		this.ibaseDao.deleteByPrimaryKey("Mobilew_deleteBxd", map);
		this.ibaseDao.updateByPrimaryKey("Mobilew_updateZd", map);
		// 删除流程实例
		this.taskService.deleteProcInstByProcInstId(procInstId);
	}

	@Override
	public void deleteProcInstAndRebootBxdProc() {
		List<Bxd> list = (List) ibaseDao.selectListByPara("Init_selectDeleteProcInstAndRebootProcessBxd", null);
		for (Bxd bxd : list) {
			this.taskService.deleteProcInstByProcInstId(bxd.getProcInstId());
			this.starProcess(bxd.getYgbh(), bxd.getBxdh(), bxd.getBmbh(), bxd.getBxje().toString(),bxd.getBxzt());
		}
	}
}
