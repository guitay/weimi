package com.tiancom.pas.ticket;

import java.util.List;
import java.util.Map;

import com.tiancom.pas.activiti.bean.TaskFlow;
import com.tiancom.pas.common.result.ResultBean;
import com.tiancom.pas.common.result.ResultCommon;
import com.tiancom.pas.common.result.ResultListData;
import com.tiancom.pas.common.result.ResultMap;
import com.tiancom.pas.jz.Jzd;

/**
 * 机票预订
 * 
 * @author NongFei
 *
 */
public interface TicketService {

	public static final String KEY = "Ticket.TicketService";

	/**
	 * 保存机票预订单
	 * 
	 * @param dd
	 */
	public ResultCommon saveTicket(Jpdd dd);

	/**
	 * 保存机票预定单并启动BTP流程
	 * 
	 * @param dd
	 */
	public ResultCommon saveTicketAndStartBTP(Jpdd dd);
	
	/**
	 * 按条件查询机票预定单总条数
	 * @param ddbh TODO
	 * @param bmid
	 * @param ygid
	 * @param startRow
	 * @param pageSize
	 * 
	 * @return
	 */
	public ResultMap queryCountByCondition(String ddbh, String bmid, String ygid, String dlr);

	/**
	 * 按条件查询机票预定单
	 * @param ddbh TODO
	 * @param bmid
	 * @param ygid
	 * @param startRow
	 * @param pageSize
	 * 
	 * @return
	 */
	public ResultListData<Jpdd> queryByCondition(String ddbh, String bmid, String ygid, String dlr,int startRow, int pageSize);

	/**
	 * 删除订单
	 * 
	 * @param ddbh
	 * @param procInstId
	 *            TODO
	 * @return
	 */
	public ResultCommon deleteTicketByDdbh(Integer ddbh, String procInstId);

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
	public ResultListData<Jpdd> loadMyAuditingTasks(String ygbh);

	/**
	 * 借支单已审批列表
	 * 
	 * @param ygbh
	 * @return
	 */
	public ResultListData<Jpdd> loadMyAuditedTasks(String ygbh);
	
	public ResultBean<Jpdd> loadJpddByPk(Integer ddbh);
	
	public List loadLC(String procinstid);
}
