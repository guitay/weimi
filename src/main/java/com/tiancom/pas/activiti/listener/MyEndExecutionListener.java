package com.tiancom.pas.activiti.listener;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.VariableInstance;

//@Component
public class MyEndExecutionListener implements ExecutionListener {

//	@Autowired
//	private HyzhgxglService service;

	/**
	 * 
	 */
	private static final long serialVersionUID = 6271686713193360812L;

	public void notify(DelegateExecution execution) throws Exception {
		Map<String, VariableInstance> map = execution.getVariableInstances();
		String procDefId = execution.getProcessDefinitionId();
		String procInsId = execution.getProcessInstanceId();
		Integer jxdxdh = (Integer) map.get("jxdxdh").getValue();
		Integer khdxdh = (Integer) map.get("applierKhdxdh").getValue();
		String approved = (String) map.get("approved").getValue();

		if (approved.equals("1")) {
//			List<HyZhGxGlYjgx> newRelList = service.selectNewRelationWithProc(procDefId, procInsId, jxdxdh);
//			service.builtNewRelation(jxdxdh, 1, null, newRelList.get(0).getQsrq(), khdxdh, newRelList, null, null);
		}
		
//		ProcessInstance pi = execution.getEngineServices().getRuntimeService().createProcessInstanceQuery().processInstanceId(procInsId)
//				.singleResult();
//		String isActive = pi.isEnded()?"1":"0";
//		service.updateProcState(procDefId, procInsId, isActive);

		// 更新proc_yjsp_ck.is_alive状态
	}

}
