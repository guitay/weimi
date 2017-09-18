package com.tiancom.pas.findept;

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

import com.tiancom.pas.common.framework.context.LoginUser;
import com.tiancom.pas.common.framework.spring.SpringAppContext;
import com.tiancom.pas.common.framework.struts.PasBaseAction;
import com.tiancom.pas.common.json.JsonUtil;
import com.tiancom.pas.common.util.PasException;
import com.tiancom.pas.common.util.Grid.easyui.PageUtil;
import com.tiancom.pas.jz.JzService;
import com.tiancom.pas.mobilew.MobilewService;

public class FinDeptAction extends PasBaseAction {

	protected Class setClass() {
		return this.getClass();
	}
	
	@Override
	protected void setQueryMap(HttpServletRequest request) {
		super.setQueryMap(request);
		FinDeptService ts = (FinDeptService) SpringAppContext.getBean(FinDeptService.KEY, request);
		List<Map> xmlist = ts.loadXmList();
		request.setAttribute("xmList",xmlist);
	}

	public ActionForward toPage(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws PasException {
		String sysDate = getLoginUser(request).getSysDate();
		request.setAttribute("sysDate", sysDate);
		request.setAttribute("count", 0);
		setQueryMap(request);
		setForm(actionForm, request);
		return mapping.findForward("List");
	}
	/**
	 * to财务审批页面
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws PasException
	 */
	public ActionForward toAuditPage(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws PasException {
		String sysDate = getLoginUser(request).getSysDate();
		request.setAttribute("sysDate", sysDate);
		request.setAttribute("count", 0);
		setQueryMap(request);
		setForm(actionForm, request);
		return mapping.findForward("auditList");
	}
	
	/**
	 * to账单查询页面
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws PasException
	 */
	public ActionForward toWmZdPage(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws PasException {
		String sysDate = getLoginUser(request).getSysDate();
		request.setAttribute("sysDate", sysDate);
		request.setAttribute("count", 0);
		setQueryMap(request);
		setForm(actionForm, request);
		return mapping.findForward("wmzdList");
	}
	

	/**
	 * to出纳页面
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws PasException
	 */
	public ActionForward toCashierPage(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws PasException {
		String sysDate = getLoginUser(request).getSysDate();
		request.setAttribute("sysDate", sysDate);
		request.setAttribute("count", 0);
		setQueryMap(request);
		setForm(actionForm, request);
		return mapping.findForward("cashierList");
	}
	

	/**
	 * to助理页面
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws PasException
	 */
	public ActionForward toAssistantPage(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws PasException {
		String sysDate = getLoginUser(request).getSysDate();
		request.setAttribute("sysDate", sysDate);
		request.setAttribute("count", 0);
		setQueryMap(request);
		setForm(actionForm, request);
		return mapping.findForward("assistantList");
	}
	

	/**
	 * 取得审批待办任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAditingTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map params = readParameters(request);
		FinDeptService ts = (FinDeptService) SpringAppContext.getBean(FinDeptService.KEY, request);
		List<Bxd> datas = ts.loadAuditingTaskList(params);
		int count = datas!=null? datas.size():0;
		JSONObject object = new JSONObject();
		object.put("total", count);
		if (count > 0) {
			JSONArray a = JsonUtil.covertListToJsonArray("audit", datas);
			object.put("rows", a);
		} else {
			object.put("rows", new JSONArray());
		}
		
		String jSelObject = object.toString();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(jSelObject);
		response.addHeader("X-JSON", "true");

		return null;
	}

	/**
	 * 取得出纳待办任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCashierTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map params = readParameters(request);
		FinDeptService ts = (FinDeptService) SpringAppContext.getBean(FinDeptService.KEY, request);
		List<Bxd> datas = ts.loadCashierTaskList(params);
		int count = datas!=null? datas.size():0;
		JSONObject object = new JSONObject();
		object.put("total", count);
		if (count > 0) {
			JSONArray a = JsonUtil.covertListToJsonArray("cashier", datas);
			object.put("rows", a);
		} else {
			object.put("rows", new JSONArray());
		}
		String jSelObject = object.toString();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(jSelObject);
		response.addHeader("X-JSON", "true");

		return null;
	
	}
	/**
	 * 取得助理待办任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAssistantTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map params = readParameters(request);
		FinDeptService ts = (FinDeptService) SpringAppContext.getBean(FinDeptService.KEY, request);
		List<Bxd> datas = ts.loadAssistantTaskList(params);
		int count = datas!=null? datas.size():0;
		JSONObject object = new JSONObject();
		object.put("total", count);
		if (count > 0) {
			JSONArray a = JsonUtil.covertListToJsonArray("assistant", datas);
			object.put("rows", a);
		} else {
			object.put("rows", new JSONArray());
		}
		String jSelObject = object.toString();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(jSelObject);
		response.addHeader("X-JSON", "true");

		return null;
	
	}
	

	/**
	 * 取得维秘账单列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getWmZdList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map params = readParameters(request);
		FinDeptService ts = (FinDeptService) SpringAppContext.getBean(FinDeptService.KEY, request);

		PageUtil page = new PageUtil(request);
		
		int count = ts.loadWmZdCount(params);
		JSONObject object = new JSONObject();
		object.put("total", count);
		if (count > 0) {
			List<WmZd> datas = ts.loadWmZdList(params, page.getStartRows(), page.getRows());
			JSONArray a = JsonUtil.covertListToJsonArray("wmzd", datas);
			object.put("rows", a);
		} else {
			object.put("rows", new JSONArray());
		}
		String jSelObject = object.toString();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(jSelObject);
		response.addHeader("X-JSON", "true");

		return null;
	
	}

	private Map readParameters(HttpServletRequest request) {
		String bxdh = request.getParameter("bxdh");
		String xmbh = request.getParameter("xmbh");
		String jgkhdxdh = request.getParameter("jgkhdxdh");
		String khdxdh = request.getParameter("khdxdh");
		String ygbh = (String)this.getLoginUser(request).getDlmc();
		
		Map map = new HashMap();
		map.put("bxdh", bxdh);
		map.put("xmbh", xmbh);
		map.put("jgkhdxdh", jgkhdxdh);
		map.put("khdxdh", khdxdh);
		map.put("ygbh", ygbh);

		map.put("czzt", request.getParameter("czzt"));//处理状态、出帐状态、会计的审核状态
		
		return map;
	}
	
	/**
	 * 完成财务审批（出纳出账）任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward completeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginUser user = (LoginUser)this.getLoginUser(request);
		String ygbh = user.getDlmc();
		String taskId = request.getParameter("taskId");

		String approved = request.getParameter("approved");
		Map variables = new HashMap();
		variables.put("approved", Boolean.valueOf(approved));
		
		FinDeptService ts = (FinDeptService) SpringAppContext.getBean(FinDeptService.KEY, request);
		ts.completeTask(taskId, variables, ygbh);

		return null;
	}

	public ActionForward getTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String bxdh = request.getParameter("bxdh");
		String bmid = request.getParameter("jgkhdxdh");
		String ygbh = request.getParameter("khdxdh_dh");
		String dlr = (String)this.getLoginUser(request).getDlmc();
		
		Map map = new HashMap();
		map.put("bxdh", bxdh);
		map.put("bmid", bmid);
		map.put("dlr", dlr);
		map.put("ygbh", ygbh);
		FinDeptService ts = (FinDeptService) SpringAppContext.getBean(FinDeptService.KEY, request);
		List<Bxd> datas = ts.loadTaskList(map);
		int count = datas!=null? datas.size():0;
		JSONObject object = new JSONObject();
		object.put("total", count);
		if (count > 0) {
			JSONArray a = JsonUtil.covertListToJsonArray("audit", datas);
			object.put("rows", a);
		} else {
			object.put("rows", new JSONArray());
		}
		
		String jSelObject = object.toString();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(jSelObject);
		response.addHeader("X-JSON", "true");

		return null;
	}
	
	public ActionForward loadZD(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FinDeptService serv = (FinDeptService) getBean(FinDeptService.KEY, request);
		String bxdh = request.getParameter("bxdh");
		
		List lc  = serv.loadZD(bxdh);
		request.setAttribute("zd", lc);
		return mapping.findForward("bxdzd");
	}
	
	public ActionForward loadLC(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FinDeptService serv = (FinDeptService) getBean(FinDeptService.KEY, request);
		String procinstid = request.getParameter("procinstid");
		
		List lc  = serv.loadLC(procinstid);
		request.setAttribute("lc", lc);
		return mapping.findForward("bxdlc");
	}

//	public ActionForward getBxdDetail(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		FinDeptService serv = (FinDeptService)getBean(FinDeptService.KEY, request);
//		String ygbh = request.getSession().getAttribute("ygbh").toString();
//		String bxdh = request.getParameter("bxdh");
//		String bmmc = request.getParameter("bmmc");
//		String xm = request.getParameter("xm");
//		String taskId = request.getParameter("taskId");
//		
//		if(bmmc != null){
//			bmmc = new String( bmmc.getBytes("iso-8859-1"), "utf-8");
//		}
//		if(xm != null){
//			xm = new String(xm.getBytes("iso-8859-1"), "utf-8");
//		}
//		
//		Map map = new HashMap();
//		map.put("bxdh", bxdh);
//		
//		List<Map> bxd = serv.loadBxdByBxdh(bxdh);
//		List<Map> zd = serv.selectBxdZdxqHz(map);
//		
//		request.setAttribute("bxd",bxd);
//		request.setAttribute("zd",zd);
//		
//		return mapping.findForward("bxdDetail");
//	}
}
