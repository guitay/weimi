package com.tiancom.pas.jz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tiancom.pas.activiti.bean.TaskFlow;
import com.tiancom.pas.common.result.ResultBean;
import com.tiancom.pas.common.result.ResultCommon;
import com.tiancom.pas.common.result.ResultListData;
import com.tiancom.pas.common.result.ResultMap;
import com.tiancom.pas.mobilelogin.Rsglygxx;

/**
 * 借支单
 * 
 * @author NongFei
 *
 */
public interface JzService {

	public static final String KEY = "JZ.JZService";

	/**
	 * 保存借支单
	 * 
	 * @param dd
	 */
	public ResultCommon saveJzd(Jzd dd);

	/**
	 * 保存借支单并启动CIAP流程
	 * 
	 * @param dd
	 */
	public ResultCommon saveJzdAndStartCIAP(Jzd dd);

	/**
	 * 按条件查询借支单总条数
	 * 
	 * @param jzdh
	 *            TODO
	 * @param xmid
	 *            TODO
	 * @param bmid
	 * @param ygid
	 * @param startRow
	 * @param pageSize
	 * @return
	 */
	public ResultMap queryCountByCondition(String jzdh, String xmid, String bmid, String ygid, String dlr);

	/**
	 * 按借支单号加载jzd
	 * 
	 * @param jzdh
	 * @return
	 */
	public ResultBean<Jzd> loadJzdByPk(Integer jzdh);

	/**
	 * 按条件查询借支单
	 * 
	 * @param jzdh
	 *            TODO
	 * @param xmbh
	 *            TODO
	 * @param bmid
	 * @param ygbh
	 * @param startRow
	 * @param pageSize
	 * @return
	 */
	public ResultListData<Jzd> queryByCondition(String jzdh, String xmbh, String bmid, String ygbh);

	/**
	 * 按条件查询借支单
	 * 
	 * @param jzdh
	 *            TODO
	 * @param xmbh
	 *            TODO
	 * @param bmid
	 * @param ygid
	 * @param startRow
	 * @param pageSize
	 * @return
	 */
	public ResultListData<Jzd> queryByCondition(String jzdh, String xmbh, String bmid, String ygbh,String dlr, int startRow,
			int pageSize);

	/**
	 * 删除订单
	 * 
	 * @param jzdh
	 * @param procInstId
	 *            TODO
	 * @return
	 */
	public ResultCommon deleteJzdByJzdh(Integer jzdh, String procInstId);

	/**
	 * 取得项目列表
	 * 
	 * @return
	 */
	public List<Map> loadXmList();

	/**
	 * 借支单待审批列表
	 * 
	 * @param ygbh
	 * @return
	 */
	public ResultListData<Jzd> loadMyAuditingTasks(String ygbh);

	/**
	 * 借支单已审批列表
	 * 
	 * @param ygbh
	 * @return
	 */
	public ResultListData<Jzd> loadMyAuditedTasks(String ygbh);

	/**
	 * 是否为“收账确认”任务	
	 * @param taskId
	 * @return
	 */
	public boolean isSzqrTask(String taskId);
	
	public List loadLC(String procinstid);
}
