package com.tiancom.pas.mobilew;

import java.util.List;
import java.util.Map;

public interface MobilewService {

	final static String KEY = "MobilewService";

	public String zxbd(Map map);
	
	/**
	 * 我的报销单
	 * @param map
	 * @return
	 */
	public List<Bxd> selectBxdLb(Map map);
	
	/**
	 * 我的报销单（被退回）
	 * @param map
	 * @return
	 */
	public List<Bxd> selectBxdLb_btg(Map map);
	
	public Bxd selectBxdxq(Map map);
	
	public List selectBxdZdxq(Map map);
	
	public List<Zdxq> selectBxdZdxqHz(Map map);
	
	public void deleteBxd(Map map);
	
	public void updateBxd(Map map);
	
	public List selectBxspLb(Map map);
	
	public void TyBxd(Map map);
	
	public void BtyBxd(Map map);
	
	public void updateBxdZt(Map map);
	
	public String getGw(Map map);
	
	public void Szqr(Map map);
	/**
	 * 我的审批(待审批列表)
	 * @param ygbh
	 * @return
	 */
	public List<Bxd> loadMyAuditingTasks(String ygbh);
	
	/**
	 * 我的审批(已审批列表)
	 * @param ygbh
	 * @return
	 */
	public List<Bxd> loadMyAuditedTasks(String ygbh);

	/**
	 * 是否为“收账确认”任务	
	 * @param taskId
	 * @return
	 */
	public boolean isSzqrTask(String taskId);
}
