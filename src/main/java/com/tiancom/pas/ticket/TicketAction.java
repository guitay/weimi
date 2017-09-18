package com.tiancom.pas.ticket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tiancom.pas.activiti.task.NBTaskService;
import com.tiancom.pas.common.framework.spring.SpringAppContext;
import com.tiancom.pas.common.framework.struts.PasBaseAction;
import com.tiancom.pas.common.json.JsonUtil;
import com.tiancom.pas.common.result.ResultCommon;
import com.tiancom.pas.common.result.ResultMap;
import com.tiancom.pas.common.util.PasException;
import com.tiancom.pas.common.util.Grid.easyui.PageUtil;
import com.tiancom.pas.jz.JzService;
import com.tiancom.pas.mobilelogin.Rsglygxx;

/**
 * 机票预订
 * 
 * @author NongFei
 *
 */
public class TicketAction extends PasBaseAction {

	protected Class setClass() {
		return this.getClass();
	}

	@Override
	protected void setQueryMap(HttpServletRequest request) {
		super.setQueryMap(request);
		TicketService ts = (TicketService) SpringAppContext.getBean(TicketService.KEY, request);
		List<Map> xmlist = ts.loadXmList();
		request.setAttribute("xmList", xmlist);
	}

	/**
	 * to机构预订查询页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws PasException
	 */
	public ActionForward toTicketPage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws PasException {
		String sysDate = getLoginUser(request).getSysDate();
		request.setAttribute("sysDate", sysDate);
		request.setAttribute("count", 0);
		setQueryMap(request);
		setForm(actionForm, request);
		return mapping.findForward("list");
	}

	public ActionForward saveTicket(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Date s = new Date(request.getParameter("cfsj"));
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		String cfsj = f.format(s);
		String ddbh = request.getParameter("ddbh");
		String xmbh = request.getParameter("xmbh");
		String khbh = request.getParameter("khbh");
		Rsglygxx yg = (Rsglygxx) request.getSession().getAttribute("yg");
		Integer ygid = yg.getYgid();
		String ygbh = yg.getYgbh();
		String bmbh = yg.getBmbh();
		String cfd = request.getParameter("cfd");
		String mdd = request.getParameter("mdd");
		String jyhb = request.getParameter("jyhb");
		String comment = request.getParameter("comment");

		Jpdd dd = new Jpdd();
		if (ddbh != "" && ddbh != null) {
			dd.setDdbh(Integer.valueOf(ddbh));
		}
		if (xmbh != "" && xmbh != null) {
			dd.setXmbh(Integer.valueOf(xmbh));
		}
		if (khbh != "" && khbh != null) {
			dd.setKhbh(Integer.valueOf(khbh));
		}
		dd.setCfd(cfd);
		dd.setMdd(mdd);
		dd.setCfsj(cfsj);
		dd.setJyhb(jyhb);
		dd.setComment(comment);
		dd.setYgbh(Integer.valueOf(ygbh));
		dd.setBmbh(bmbh);

		TicketService ts = (TicketService) SpringAppContext.getBean(TicketService.KEY, request);
		ResultCommon rc = ts.saveTicketAndStartBTP(dd);
		JSONObject object = new JSONObject();
		object.put(String.valueOf(rc.getCode()), rc.getDesc());
		String jSelObject = object.toString();
		writeToResponse(response, jSelObject);

		return null;
	}

	public ActionForward MyTicket(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TicketService ts = (TicketService) SpringAppContext.getBean(TicketService.KEY, request);
		PageUtil page = new PageUtil(request);
		Rsglygxx yg = (Rsglygxx) request.getSession().getAttribute("yg");
		String ygbh = yg.getYgbh();
		List<Jpdd> datas = ts.queryByCondition(null, null, ygbh,null, page.getStartRows(), page.getRows()).getRows();
		
		request.setAttribute("ddlb", datas);
		request.setAttribute("count", datas.size());

		return mapping.findForward("wddd");
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
		TicketService serv = (TicketService) getBean(TicketService.KEY, request);
		Rsglygxx yg = (Rsglygxx) request.getSession().getAttribute("yg");

		Map map = new HashMap();
		map.put("ygbh", yg.getYgbh());

		List<Jpdd> dsplb = serv.loadMyAuditingTasks(yg.getYgbh()).getRows();
		List<Jpdd> ysplb = serv.loadMyAuditedTasks(yg.getYgbh()).getRows();

		request.setAttribute("dsplb", dsplb);
		request.setAttribute("ysplb", ysplb);

		return mapping.findForward("ddsp");
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
	public ActionForward ddxq(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TicketService serv = (TicketService) getBean(TicketService.KEY, request);
		Rsglygxx yg = (Rsglygxx)request.getSession().getAttribute("yg");
		
		String ddbh = request.getParameter("ddbh");
		String taskId = request.getParameter("taskId");

		Jpdd jzd = serv.loadJpddByPk(Integer.valueOf(ddbh)).getRow();
		request.setAttribute("dd", jzd);
		request.setAttribute("sfsp", request.getParameter("sfsp"));
		request.setAttribute("backTo", request.getParameter("backTo"));
		request.setAttribute("taskId", taskId);
		
		NBTaskService nbs = (NBTaskService)getBean(NBTaskService.KEY,request);
		Task task = nbs.loadTaskDetailByTaskId(taskId);
		if(task!=null) {
			request.setAttribute("taskDefKey", task.getTaskDefinitionKey());
		}
		
		return mapping.findForward("ddxq");
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
	public ActionForward ddxg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TicketService serv = (TicketService)getBean(TicketService.KEY, request);
		Rsglygxx yg = (Rsglygxx)request.getSession().getAttribute("yg");
		String ddbh = request.getParameter("ddbh");
		String taskId = request.getParameter("taskId");
		
		Jpdd jzd = serv.loadJpddByPk(Integer.valueOf(ddbh)).getRow();
//		List<Map> BmList = serv2.selectBm(map);
		request.setAttribute("dd",jzd);
//		request.setAttribute("BmList",BmList);
		request.setAttribute("taskId", taskId);
		request.setAttribute("sfxg", "Y");
		request.setAttribute("procInstId", jzd.getTaskFlow().get(0).getProcInstId());
				
		return mapping.findForward("ddxq");
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
	public ActionForward btyJpdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JzService serv = (JzService) getBean(JzService.KEY, request);
		NBTaskService ts = (NBTaskService) getBean(NBTaskService.KEY, request);
		String jzdh = request.getParameter("ddbh");
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
	public ActionForward tyJpdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JzService serv = (JzService) getBean(JzService.KEY, request);
		NBTaskService ts = (NBTaskService) getBean(NBTaskService.KEY, request);

		String jzdh = request.getParameter("ddbh");
		String taskId = request.getParameter("taskId");
		String comment = request.getParameter("xgyj");
		String cpxx = request.getParameter("cpxx");//出票信息

		Map variables = new HashMap();
		variables.put("comment", comment);
		variables.put("approved", new Boolean(true));
		Rsglygxx yg = (Rsglygxx) request.getSession().getAttribute("yg");
		ts.completeTask(taskId, null, variables, yg.getYgbh(), null);

		return null;
	}
	/**
	 * 出票
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cpJpdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TicketService serv = (TicketService) getBean(TicketService.KEY, request);
		NBTaskService ts = (NBTaskService) getBean(NBTaskService.KEY, request);

		String ddbh = request.getParameter("ddbh");
		String taskId = request.getParameter("taskId");
		String cpxx = request.getParameter("cpxx");//出票信息
		
		Jpdd t = new Jpdd();
		t.setDdbh(Integer.valueOf(ddbh));
		t.setCpxx(cpxx);
		serv.saveTicket(t);//保存出票信息

		Map variables = new HashMap();
		variables.put("cpxx", cpxx);
		Rsglygxx yg = (Rsglygxx) request.getSession().getAttribute("yg");
		ts.completeTask(taskId, null, variables, yg.getYgbh(), null);

		return null;
	}
	/**
	 * 收票确认
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward spqr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TicketService serv = (TicketService) getBean(TicketService.KEY, request);
		NBTaskService ts = (NBTaskService) getBean(NBTaskService.KEY, request);

		String ddbh = request.getParameter("ddbh");
		String taskId = request.getParameter("taskId");

		Rsglygxx yg = (Rsglygxx) request.getSession().getAttribute("yg");
		ts.completeTask(taskId, null, null, yg.getYgbh(), null);

		return null;
	}
	

	/**
	 * 删除机票订单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteJpddByDdbh(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ddbh = request.getParameter("ddbh");
		String procInstId = request.getParameter("procInstId");
		TicketService ts = (TicketService) SpringAppContext.getBean(TicketService.KEY, request);
		ResultCommon rc = ts.deleteTicketByDdbh(Integer.valueOf(ddbh), procInstId);

		JSONObject object = new JSONObject();
		object.put(String.valueOf(rc.getCode()), rc.getDesc());
		String jSelObject = object.toString();
		writeToResponse(response, jSelObject);

		return null;
	}

	/**
	 * 按条件查询机票订单
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
		String ddbh = request.getParameter("ddbh");
		String bmid = request.getParameter("jgkhdxdh");
		String ygbh = request.getParameter("khdxdh_dh");
		String dlr = (String)this.getLoginUser(request).getDlmc();

		TicketService ts = (TicketService) SpringAppContext.getBean(TicketService.KEY, request);
		ResultMap rc = ts.queryCountByCondition(ddbh, bmid, ygbh,dlr);

		PageUtil page = new PageUtil(request);
		int count = ((Integer) rc.getRows().get("count")).intValue();
		JSONObject object = new JSONObject();
		object.put("total", count);
		if (count > 0) {
			List<Jpdd> datas = ts.queryByCondition(ddbh, bmid, ygbh,dlr, page.getStartRows(), page.getRows()).getRows();
			JSONArray a = JsonUtil.covertListToJsonArray("ticket", datas);
			object.put("rows", a);
		} else {
			object.put("rows", new JSONArray());
		}
		writeToResponse(response, object.toString());

		return null;
	}

	private void writeToResponse(HttpServletResponse response, String jSelObject) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(jSelObject);
		response.addHeader("X-JSON", "true");
	}
	
	public ActionForward loadLC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TicketService serv = (TicketService)getBean(TicketService.KEY, request);
		String procinstid = request.getParameter("procinstid");
		
		List lc  = serv.loadLC(procinstid);
		request.setAttribute("lc", lc);
				
		return mapping.findForward("ddlc");
	}

}
