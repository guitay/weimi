package com.tiancom.pas.mobilew;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tiancom.pas.activiti.task.NBTaskService;
import com.tiancom.pas.common.framework.struts.PasBaseAction;
import com.tiancom.pas.common.proc.entity.QueryUtilPojo;
import com.tiancom.pas.common.proc.service.QueryUtilService;
import com.tiancom.pas.mobilelogin.Rsglygxx;
import com.tiancom.pas.mobilezd.MobilezdService;

public class MobilewAction extends PasBaseAction {

	protected Class setClass() {
		return this.getClass();
	}
		
	//点击上天下天 重新拉取最近历史信息
	public ActionForward zxbd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobilewService serv2 = (MobilewService)getBean(MobilewService.KEY, request);
		String openid = request.getParameter("openid");
		Map map = new HashMap();
		map.put("openid", openid);
		String msg = serv2.zxbd(map);
		response.getWriter().print(msg);
		return null;
	}
	
	public ActionForward wdbx(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobilewService serv = (MobilewService)getBean(MobilewService.KEY, request);
		String ygbh = request.getSession().getAttribute("ygbh").toString();
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		
		List<Bxd> wdbx = serv.selectBxdLb(map);
		List<Bxd> wdbx2 = serv.selectBxdLb_btg(map);
		wdbx.addAll(wdbx2);
		request.setAttribute("bxlb",wdbx);
		request.setAttribute("count",wdbx.size());
		
		return mapping.findForward("wdbx");
	}	
	
	public ActionForward Bxdxq(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobilewService serv = (MobilewService)getBean(MobilewService.KEY, request);
		String ygbh = request.getSession().getAttribute("ygbh").toString();
		String bxdh = request.getParameter("bxdh");
		String bmmc = request.getParameter("bmmc");
		String xm = request.getParameter("xm");
		String taskId = request.getParameter("taskId");
		
		if(bmmc != null){
			bmmc = new String( bmmc.getBytes("iso-8859-1"), "utf-8");
		}
		if(xm != null){
			xm = new String(xm.getBytes("iso-8859-1"), "utf-8");
		}
		
		Map map = new HashMap();
		map.put("bxdh", bxdh);
		
		Bxd bxd = serv.selectBxdxq(map);
		List<Zdxq> zd = serv.selectBxdZdxqHz(map);
		
		request.setAttribute("bxd",bxd);
		request.setAttribute("zd",zd);
		request.setAttribute("sfsp",request.getParameter("sfsp"));
		request.setAttribute("backTo",request.getParameter("backTo"));
		request.setAttribute("bmmc",bmmc);
		request.setAttribute("xm",xm);
		request.setAttribute("taskId",taskId);
		boolean isSzqrTask = serv.isSzqrTask(taskId);
		request.setAttribute("sfszqr", isSzqrTask);//任务是否为收账确认
		
		return mapping.findForward("bxdxq");
	}
	
	public ActionForward deleteBxd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobilewService serv = (MobilewService)getBean(MobilewService.KEY, request);
		String bxdh = request.getParameter("bxdh");
		String taskId = request.getParameter("taskId");
		Map map = new HashMap();
		map.put("bxdh", bxdh);
		map.put("taskId", taskId);
		serv.deleteBxd(map);
		return null;
	}
	
	public ActionForward Bxdxg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobilewService serv = (MobilewService)getBean(MobilewService.KEY, request);
		MobilezdService serv2 = (MobilezdService)getBean(MobilezdService.KEY, request);
		String ygbh = request.getSession().getAttribute("ygbh").toString();
		String bxdh = request.getParameter("bxdh");
		String taskId = request.getParameter("taskId");
		
		Map map = new HashMap();
		map.put("bxdh", bxdh);
		
		Bxd bxd = serv.selectBxdxq(map);
		List<Map> zd = serv.selectBxdZdxq(map);
		List<Map> BmList = serv2.selectBm(map);
		request.setAttribute("bxd",bxd);
		request.setAttribute("zd",zd);
		request.setAttribute("BmList",BmList);
		request.setAttribute("taskId", taskId);
				
		return mapping.findForward("bxdxg");
	}
	
	public ActionForward changeZdBxd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MobilezdService serv = (MobilezdService)getBean(MobilezdService.KEY, request);
		String zdbh = request.getParameter("zdbh");
		String bxdh = request.getParameter("bxdh");
		String taskId = request.getParameter("taskId");
		Map map = new HashMap();
		map.put("zdbh", zdbh);
		
		List<Map> Zdxx = serv.selectZdByZdbh(map);
		request.setAttribute("Zdxx",Zdxx);
		request.setAttribute("bxdh",bxdh);
		request.setAttribute("taskId", taskId);
		return mapping.findForward("ytjzdbj");
	}
	
	public ActionForward updateZdBxd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String zdbh = request.getParameter("zdbh");
		String zdje = request.getParameter("zdje");
		String zdlx = request.getParameter("zdlx");
		String zdsm = request.getParameter("zdsm");
		String rq = request.getParameter("rq");
		MobilezdService serv = (MobilezdService)getBean(MobilezdService.KEY, request);
		MobilewService serv2 = (MobilewService)getBean(MobilewService.KEY, request);
		
		Map map = new HashMap();
		map.put("zdbh", zdbh);
		map.put("zdje", zdje);
		map.put("zdlx", zdlx);
		map.put("zdsm", zdsm);
		map.put("rq", rq);
		serv.updateZd(map);
		serv2.updateBxd(map);
		return null;
	}
	/**
	 * 我的审批(待审批\已审批列表)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward bxsplb(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobilewService serv = (MobilewService)getBean(MobilewService.KEY, request);
		String ygbh = request.getSession().getAttribute("ygbh").toString();
		Map map = new HashMap();
		map.put("ygbh", ygbh);

		List<Bxd> bxsp = serv.loadMyAuditingTasks(ygbh);
		request.setAttribute("bxsplb",bxsp);
		List<Bxd> bxYsp = serv.loadMyAuditedTasks(ygbh);
		request.setAttribute("bxYsplb",bxYsp);
		
		request.setAttribute("dspcount",bxsp.size());
		request.setAttribute("yspcount",bxYsp.size());
		return mapping.findForward("bxsplb");
	}
	
	/**
	 * 同意报销单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward TyBxd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobilewService serv = (MobilewService)getBean(MobilewService.KEY, request);
		NBTaskService ts = (NBTaskService)getBean(NBTaskService.KEY, request);
		
		String bxdh = request.getParameter("bxdh");
		String taskId = request.getParameter("taskId");
		String comment = request.getParameter("xgyj");
		
		Map variables = new HashMap();
		variables.put("comment", comment);
		variables.put("approved", new Boolean(true));
		String ygbh = (String)request.getSession().getAttribute("ygbh");
		ts.completeTask(taskId, null, variables, ygbh, null);
		
		return null;
	}
	
	public ActionForward BtyBxd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobilewService serv = (MobilewService)getBean(MobilewService.KEY, request);
		NBTaskService ts = (NBTaskService)getBean(NBTaskService.KEY, request);
		String bxdh = request.getParameter("bxdh");
		String taskId = request.getParameter("taskId");
		String comment = request.getParameter("xgyj");
		
		Map map = new HashMap();
		map.put("bxdh", bxdh);
		map.put("xgyj", comment);
		serv.BtyBxd(map);

		Map variables = new HashMap();
		variables.put("comment", comment);
		variables.put("approved", new Boolean(false));
		String ygbh = (String)request.getSession().getAttribute("ygbh");
		ts.completeTask(taskId, null, variables, ygbh, null);
		
		return null;
	}
	
	public ActionForward updateBxdZt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobilewService serv = (MobilewService)getBean(MobilewService.KEY, request);
		String bxdh = request.getParameter("bxdh");
		String taskId = request.getParameter("taskId");
		String bxje = request.getParameter("bxje");
	    String bxbm = request.getParameter("bxbm");
	    String qsrq = request.getParameter("qsrq");
		String jsrq = request.getParameter("jsrq");
		String ccts = request.getParameter("ccts");
		Map map = new HashMap();
		map.put("bxdh", bxdh);
		map.put("bxje", bxje);
		map.put("qsrq", qsrq);
		map.put("jsrq", jsrq);
		map.put("bxbm", bxbm);
		map.put("ccts", ccts);
		serv.updateBxdZt(map);
		
		NBTaskService ts = (NBTaskService)getBean(NBTaskService.KEY,request);
		QueryUtilService qs = (QueryUtilService)getBean(QueryUtilService.KEY,request);

		String ygbh = (String)request.getSession().getAttribute("ygbh");
		Map varMap = new HashMap();
		QueryUtilPojo rootJg = qs.selectByKey2(Integer.valueOf(bxbm));
		String bmbh = rootJg.getBmbh();
		varMap.put("bmbh", bmbh);
		varMap.put("bxje", bxje);
		
		ts.completeTask(taskId,null , varMap, ygbh, null);
		
		return null;
	}
	
	public ActionForward getGw(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobilewService serv = (MobilewService)getBean(MobilewService.KEY, request);
		String ygbh = request.getSession().getAttribute("ygbh").toString();
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		String msg = serv.getGw(map);
		response.getWriter().print(msg);
		return null;
	}
	//收账确认
	public ActionForward Szqr(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobilewService serv = (MobilewService)getBean(MobilewService.KEY, request);
		NBTaskService ts = (NBTaskService)getBean(NBTaskService.KEY, request);
		
		String bxdh = request.getParameter("bxdh");
		String taskId = request.getParameter("taskId");

		String ygbh = (String)request.getSession().getAttribute("ygbh");
		ts.completeTask(taskId, null, null, ygbh, null);
		
		return null;
	
	}
}
