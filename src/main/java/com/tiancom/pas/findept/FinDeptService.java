package com.tiancom.pas.findept;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.tiancom.pas.activiti.bean.TaskFlow;

@Component
public interface FinDeptService {

	public static final String KEY = "FinDept.FinDeptService";

	/**
	 * 取得项目列表
	 * 
	 * @return
	 */
	public List<Map> loadXmList();

	/**
	 * 审批待办列表
	 * 
	 * @param map
	 *            TODO
	 * @param processDefinitionKey
	 *            TODO
	 * @param record
	 * @param jlrdh
	 *            登陆用户,为了添加修改日志需要.
	 */
	public List<Bxd> loadAuditingTaskList(Map map);

	/**
	 * 出纳待办列表
	 * 
	 * @param params
	 *            TODO
	 * @param processDefinitionKey
	 *            TODO
	 * @param record
	 * @param jlrdh
	 *            登陆用户,为了添加修改日志需要.
	 */
	public List<Bxd> loadCashierTaskList(Map params);
	
	/**
	 * 助理待办任务
	 * @param params
	 * @return
	 */
	public List<Bxd> loadAssistantTaskList(Map params);

	/**
	 * 完成财务审批(或出纳出账)任务
	 * 
	 * @param taskId
	 * @param variables
	 * @param ygbh
	 */
	public void completeTask(String taskId, Map variables, String ygbh);
	
	/**
	 * 维秘账单数量
	 * @param map
	 * @return
	 */
	public int loadWmZdCount(Map map);
	
	/**
	 * 按报销单号查询报销单详情及其流转信息
	 * @param bxdh
	 * @return
	 */
	public Bxd loadBxdByBxdh(String bxdh);
	
	
	/**
	 * 维秘账单查询
	 * @param map
	 * @param startRow
	 * @param pageSize
	 * @return
	 */
	public List<WmZd> loadWmZdList(Map map, int startRow, int pageSize);
	

	/**
	 * 报销单流程流转详情
	 * @param map
	 * @param startRow
	 * @param pageSize
	 * @return
	 */
	public List<TaskFlow> loadBxdTaskFlow(Integer bxdh);
	
	public List<Bxd> loadTaskList(Map params);
	
	public List loadZD(String bxdh);
	
	public List loadLC(String procinstid);

}
