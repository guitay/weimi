package com.tiancom.pas.activiti.listener;

import java.math.BigDecimal;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.VariableInstance;

//import com.pas.cloud.api.hyzhgxgl.HyzhgxglService;

//@Component
/**
 * CCEP流程报销金额路由
 * @author NongFei
 *
 */
public class BxJeDecitionRouter implements TaskListener {
	

//	@Autowired
//	private HyzhgxglService service;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8559693543835276040L;

	public void notify(DelegateTask task) {
		Map<String,VariableInstance> variables = task.getVariableInstances();
		VariableInstance vi = (VariableInstance)variables.get("approved");
		Boolean approved = (Boolean)vi.getValue();
		if(approved) {
			VariableInstance bxjeVi = (VariableInstance)variables.get("bxje");
			BigDecimal bxje = new BigDecimal((String)bxjeVi.getValue());
			if(bxje.compareTo(new BigDecimal(5000))==-1||bxje.compareTo(new BigDecimal(5000))==0) {//x<=5000
				task.setVariable("jeRouter", "1");
				task.setVariableLocal("jeRouter", "1");
			}else if(bxje.compareTo(new BigDecimal(30000))==-1||bxje.compareTo(new BigDecimal(30000))==0) {//x<=30000
				task.setVariable("jeRouter", "2");
				task.setVariableLocal("jeRouter", "2");
			}else if(bxje.compareTo(new BigDecimal(30000))==1) {//x>30000
				task.setVariable("jeRouter", "3");
				task.setVariableLocal("jeRouter", "3");
			}
		}

	}
}
