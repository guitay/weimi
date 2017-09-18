package com.tiancom.pas.jz;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tiancom.pas.activiti.task.NBTaskService;
import com.tiancom.pas.common.framework.spring.SpringAppContext;
import com.tiancom.pas.common.framework.struts.PasBaseAction;
import com.tiancom.pas.common.json.JsonUtil;
import com.tiancom.pas.common.proc.service.QueryUtilService;
import com.tiancom.pas.common.result.ResultCommon;
import com.tiancom.pas.common.result.ResultMap;
import com.tiancom.pas.common.util.PasException;
import com.tiancom.pas.common.util.Grid.easyui.PageUtil;
import com.tiancom.pas.mobilelogin.Rsglygxx;
import com.tiancom.pas.mobilew.MobilewService;

/**
 * 借支
 * 
 * @author NongFei
 *
 */
public class JzAction extends PasBaseAction {

	protected Class setClass() {
		return this.getClass();
	}

	@Override
	protected void setQueryMap(HttpServletRequest request) {
		super.setQueryMap(request);
		JzService ts = (JzService) SpringAppContext.getBean(JzService.KEY, request);
		List<Map> xmlist = ts.loadXmList();
		request.setAttribute("xmList", xmlist);
	}

	/**
	 * to借支单查询页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws PasException
	 */
	public ActionForward toJzPage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws PasException {
		String sysDate = getLoginUser(request).getSysDate();
		request.setAttribute("sysDate", sysDate);
		request.setAttribute("count", 0);
		setQueryMap(request);
		setForm(actionForm, request);
		return mapping.findForward("list");
	}

	/**
	 * 新建借支单，并启动流程
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveJzdAndStartProc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Jzd jzd = generateJzd(request);

		JzService ts = (JzService) SpringAppContext.getBean(JzService.KEY, request);
		ResultCommon rc = ts.saveJzdAndStartCIAP(jzd);
		JSONObject object = new JSONObject();
		object.put(String.valueOf(rc.getCode()), rc.getDesc());
		String jSelObject = object.toString();
		writeToResponse(response, jSelObject);

		return null;
	}
	

	/**
	 * 新建借支单，并完成“提交申请”任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveJzdAndCplTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Jzd jzd = generateJzd(request);
		String taskId = request.getParameter("taskId");
		
		JzService ts = (JzService) SpringAppContext.getBean(JzService.KEY, request);
		ResultCommon rc = ts.saveJzd(jzd);

//		JSONObject object = new JSONObject();
//		object.put(String.valueOf(rc.getCode()), rc.getDesc());
//		String jSelObject = object.toString();
//		writeToResponse(response, jSelObject);
		
		NBTaskService taskServ = (NBTaskService)getBean(NBTaskService.KEY,request);
		QueryUtilService qs = (QueryUtilService)getBean(QueryUtilService.KEY,request);

		Rsglygxx yg = (Rsglygxx)request.getSession().getAttribute("yg");
		Map varMap = new HashMap();
//		QueryUtilPojo rootJg = qs.selectByKey2(Integer.valueOf(bxbm));
//		String bmbh = rootJg.getBmbh();
//		varMap.put("bmbh", bmbh);
		varMap.put("jzje", jzd.getJzje().toString());
		
		taskServ.completeTask(taskId,null , varMap, yg.getYgbh(), null);

		return null;
	}

	private Jzd generateJzd(HttpServletRequest request) {
		String jzdh = request.getParameter("jzdh");
		String xmbh = request.getParameter("xmbh");
		String khbh = request.getParameter("khbh");
		Rsglygxx yg = (Rsglygxx) request.getSession().getAttribute("yg");
		Integer ygid = yg.getYgid();
		String ygbh = yg.getYgbh();
		String bmbh = yg.getBmbh();
		String jzje = request.getParameter("jzje");
		String jzrq = request.getParameter("jzrq");
		if(jzrq!=null) {
			Date s = new Date(jzrq);
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			jzrq = f.format(s);
		}
		String comment = request.getParameter("comment");

		Jzd jzd = new Jzd();
		jzd.setJzdh(jzdh != null && !jzdh.equals("") && !jzdh.equals("null") ? Integer.valueOf(jzdh) : null);
		jzd.setXmbh(xmbh != null && !xmbh.equals("") && !xmbh.equals("null") ? Integer.valueOf(xmbh) : null);
		jzd.setKhbh(khbh != null && !khbh.equals("") && !khbh.equals("null") ? Integer.valueOf(khbh) : null);
		jzd.setYgid(ygid);
		jzd.setJzje(new BigDecimal(jzje));
		jzd.setJzrq(jzrq!=null? Integer.valueOf(jzrq):null);
		jzd.setComment(comment);
		jzd.setYgbh(ygbh);
		jzd.setBmbh(bmbh);
		return jzd;
	}

	/**
	 * 删除借支单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteJzdByJzdh(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String jzdh = request.getParameter("jzdh");
		String procInstId = request.getParameter("procInstId");

		JzService ts = (JzService) SpringAppContext.getBean(JzService.KEY, request);
		ResultCommon rc = ts.deleteJzdByJzdh(Integer.valueOf(jzdh), procInstId);

		JSONObject object = new JSONObject();
		object.put(String.valueOf(rc.getCode()), rc.getDesc());
		String jSelObject = object.toString();
		writeToResponse(response, jSelObject);

		return null;
	}

	/**
	 * 我的借支单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward myJzd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JzService serv = (JzService) getBean(JzService.KEY, request);
		Rsglygxx yg = (Rsglygxx) request.getSession().getAttribute("yg");

		Map map = new HashMap();
		map.put("ygbh", yg.getYgbh());

		List<Jzd> wdjzd = serv.queryByCondition(null, null, null, yg.getYgbh()).getRows();

		request.setAttribute("jzdlb", wdjzd);
		request.setAttribute("count", wdjzd.size());

		return mapping.findForward("wdjz");
	}

	/**
	 * 借支单(待审批\已审批)列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadAuditingTaskList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JzService serv = (JzService) getBean(JzService.KEY, request);
		Rsglygxx yg = (Rsglygxx) request.getSession().getAttribute("yg");

		Map map = new HashMap();
		map.put("ygbh", yg.getYgbh());

		List<Jzd> dsplb = serv.loadMyAuditingTasks(yg.getYgbh()).getRows();
		List<Jzd> ysplb = serv.loadMyAuditedTasks(yg.getYgbh()).getRows();

		request.setAttribute("dsplb", dsplb);
		request.setAttribute("ysplb", ysplb);

		return mapping.findForward("jzsp");
	}

	/**
	 * 按条件查询借支单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryByCondition(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String jzdh = request.getParameter("jzdh");
		String xmbh = request.getParameter("xmbh");
		String bmid = request.getParameter("jgkhdxdh");
		String ygbh = request.getParameter("khdxdh_dh");
		String dlr = (String)this.getLoginUser(request).getDlmc();

		JzService ts = (JzService) SpringAppContext.getBean(JzService.KEY, request);
		ResultMap rc = ts.queryCountByCondition(null, xmbh, bmid, ygbh,dlr);

		PageUtil page = new PageUtil(request);
		int count = ((Integer) rc.getRows().get("count")).intValue();
		JSONObject object = new JSONObject();
		object.put("total", count);
		if (count > 0) {
			List<Jzd> datas = ts.queryByCondition(jzdh, xmbh, bmid, ygbh,dlr, page.getStartRows(), page.getRows())
					.getRows();
			JSONArray a = JsonUtil.covertListToJsonArray("jzd", datas);
			object.put("rows", a);
		} else {
			object.put("rows", new JSONArray());
		}
		writeToResponse(response, object.toString());

		return null;
	}

	/**
	 * 不同意借支单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward btyJzd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JzService serv = (JzService) getBean(JzService.KEY, request);
		NBTaskService ts = (NBTaskService) getBean(NBTaskService.KEY, request);
		String jzdh = request.getParameter("jzdh");
		String taskId = request.getParameter("taskId");
		String comment = request.getParameter("xgyj");

		// Map map = new HashMap();
		// map.put("jzdh", jzdh);
		// map.put("xgyj", comment);
		// serv.BtyBxd(map);

		Map variables = new HashMap();
		variables.put("comment", comment);
		variables.put("approved", new Boolean(false));
		String ygbh = (String) request.getSession().getAttribute("ygbh");
		ts.completeTask(taskId, null, variables, ygbh, null);

		return null;
	}

	/**
	 * 同意借支单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward tyJzd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JzService serv = (JzService) getBean(JzService.KEY, request);
		NBTaskService ts = (NBTaskService) getBean(NBTaskService.KEY, request);

		String jzdh = request.getParameter("jzdh");
		String taskId = request.getParameter("taskId");
		String comment = request.getParameter("xgyj");

		Map variables = new HashMap();
		variables.put("comment", comment);
		variables.put("approved", new Boolean(true));
		Rsglygxx yg = (Rsglygxx) request.getSession().getAttribute("yg");
		ts.completeTask(taskId, null, variables, yg.getYgbh(), null);

		return null;
	}

	/**
	 * 收账确认
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward szqr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MobilewService serv = (MobilewService) getBean(MobilewService.KEY, request);
		NBTaskService ts = (NBTaskService) getBean(NBTaskService.KEY, request);

		String jzdh = request.getParameter("jzdh");
		String taskId = request.getParameter("taskId");

		Rsglygxx yg = (Rsglygxx) request.getSession().getAttribute("yg");
		ts.completeTask(taskId, null, null, yg.getYgbh(), null);

		return null;

	}

	/**
	 * 借支单详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward jzdxq(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JzService serv = (JzService) getBean(JzService.KEY, request);
		Rsglygxx yg = (Rsglygxx)request.getSession().getAttribute("yg");
		
		String jzdh = request.getParameter("jzdh");
		String taskId = request.getParameter("taskId");

		Jzd jzd = serv.loadJzdByPk(Integer.valueOf(jzdh)).getRow();
		request.setAttribute("jzd", jzd);
		request.setAttribute("sfsp", request.getParameter("sfsp"));
		request.setAttribute("backTo", request.getParameter("backTo"));
		request.setAttribute("taskId", taskId);

		boolean isSzqrTask = serv.isSzqrTask(taskId);
		request.setAttribute("sfszqr", isSzqrTask);// 任务是否为收账确认

		return mapping.findForward("jzdxq");
	}
	

	/**
	 * 借支单修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward jzdxg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JzService serv = (JzService)getBean(JzService.KEY, request);
		Rsglygxx yg = (Rsglygxx)request.getSession().getAttribute("yg");
		String jzdh = request.getParameter("jzdh");
		String taskId = request.getParameter("taskId");
		
		Jzd jzd = serv.loadJzdByPk(Integer.valueOf(jzdh)).getRow();
//		List<Map> BmList = serv2.selectBm(map);
		request.setAttribute("jzd",jzd);
//		request.setAttribute("BmList",BmList);
		request.setAttribute("taskId", taskId);
		request.setAttribute("procInstId", jzd.getTaskFlow().get(0).getProcInstId());
				
		return mapping.findForward("jzdxg");
	}

	private void writeToResponse(HttpServletResponse response, String jSelObject) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(jSelObject);
		response.addHeader("X-JSON", "true");
	}
	
	public ActionForward loadLC(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JzService serv = (JzService) getBean(JzService.KEY, request);
		String procinstid = request.getParameter("procinstid");
		
		List lc  = serv.loadLC(procinstid);
		request.setAttribute("lc", lc);
		return mapping.findForward("jzdlc");
	}

}
