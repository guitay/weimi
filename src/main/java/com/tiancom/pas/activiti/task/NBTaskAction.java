package com.tiancom.pas.activiti.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.task.Task;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tiancom.pas.activiti.bean.NBTask;
import com.tiancom.pas.common.framework.spring.SpringAppContext;
import com.tiancom.pas.common.framework.struts.PasBaseAction;
import com.tiancom.pas.common.json.JsonUtil;
import com.tiancom.pas.common.util.Grid.easyui.PageUtil;
import com.tiancom.pas.jz.JzService;
import com.tiancom.pas.jz.Jzd;
import com.tiancom.pas.mobilew.Bxd;
import com.tiancom.pas.mobilew.MobilewService;
import com.tiancom.pas.mobilew.Zdxq;
import com.tiancom.pas.ticket.Jpdd;
import com.tiancom.pas.ticket.TicketService;

public class NBTaskAction extends PasBaseAction {

	protected Class setClass() {
		return this.getClass();
	}

	public ActionForward toApply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("apply");
	}

	/**
	 * 提交一个新的报销单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward startProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pid = request.getParameter("pid");
		String applier = request.getParameter("applier");
		String bmbh = request.getParameter("bmbh");
		String bxdh = request.getParameter("bxdh");

		Map variables = new HashMap();
		variables.put("applier", applier);
		variables.put("bmbh", bmbh);
		variables.put("bxdh", bxdh);

		NBTaskService ts = (NBTaskService) SpringAppContext.getBean(NBTaskService.KEY, request);
		ts.startProcessInstanceAndReturn(pid, variables);

		return mapping.findForward("list");
	}
	
	public ActionForward toTaskList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("list");
	}

	/**
	 * 取得待办任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTaskList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ygbh = (String)request.getSession().getAttribute("ygbh");
		
		NBTaskService ts = (NBTaskService) SpringAppContext.getBean(NBTaskService.KEY, request);
		List<NBTask> list = ts.loadTaskTodoList(null, ygbh).getRows();
		request.setAttribute("tasks", list);
		
		PageUtil page = new PageUtil(request);
		int count = list.size();//((Integer) rc.getRows().get("count")).intValue();
		JSONObject object = new JSONObject();
		object.put("total", count);
		if (count > 0) {
//			List<Jzd> datas = ts.queryByCondition(jzdh, xmbh, bmid, ygid, page.getStartRows(), page.getRows()).getRows();
			JSONArray a = JsonUtil.covertListToJsonArray("task", list);
			object.put("rows", a);
		} else {
			object.put("rows", new JSONArray());
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(object.toString());
		response.addHeader("X-JSON", "true");
		

		return null;
	}
	
	
	public ActionForward showTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ygbh = (String)request.getSession().getAttribute("ygbh");

		String pid = request.getParameter("procInstId");
		String taskId = request.getParameter("taskId");
		
		request.setAttribute("pid", pid);
		request.setAttribute("taskId", taskId);
		
		NBTaskService ts = (NBTaskService) SpringAppContext.getBean(NBTaskService.KEY, request);
		TaskFormData taskForm = ts.getTaskFormDataByTaskId(taskId);
		Task task = taskForm.getTask();
		request.setAttribute("taskName", task.getName());
		
		List<FormProperty> formProperties = taskForm.getFormProperties();
		request.setAttribute("taskFormProps", formProperties);
		
		Object renderedTaskForm = ts.getRenderedTaskFormByTaskId(taskId);
		request.setAttribute("renderTaskForm", renderedTaskForm);
		
		Map<String,Object> variables = ts.getTaskVariables(taskId);
		request.setAttribute("variables", variables);

		String pdi = task.getProcessDefinitionId().toLowerCase();
		if(pdi.startsWith("cep")||pdi.startsWith("mcep")||pdi.startsWith("ccep")) {
			MobilewService ms = (MobilewService) SpringAppContext.getBean(MobilewService.KEY, request);
			Map map = new HashMap();
			map.put("bxdh", variables.get("bxdh"));
			Bxd bxd = ms.selectBxdxq(map);
			List<Zdxq> zd = ms.selectBxdZdxqHz(map);
			request.setAttribute("bxd", bxd);
			request.setAttribute("zd", zd);
			request.setAttribute("detailType", "bxd");
		}else if(pdi.startsWith("btp")) {
			TicketService ticketServ = (TicketService) SpringAppContext.getBean(TicketService.KEY, request);
			Jpdd jpdd = ticketServ.loadJpddByPk(Integer.valueOf((String)variables.get("ddbh"))).getRow();
			request.setAttribute("dd", jpdd);
			request.setAttribute("detailType", "ticket");
		}else if(pdi.startsWith("ciap")) {
			JzService js = (JzService)SpringAppContext.getBean(JzService.KEY, request);
			Integer jzdh = Integer.valueOf((String)variables.get("jzdh"));
			Jzd jzd = js.loadJzdByPk(jzdh).getRow();
			request.setAttribute("jzd", jzd);
			request.setAttribute("detailType", "jzd");
		}
		
		return mapping.findForward("task");
	}
	
	
	public ActionForward completeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ygbh = (String)request.getSession().getAttribute("ygbh");
		String taskId = request.getParameter("taskId");
		

		NBTaskService ts = (NBTaskService) SpringAppContext.getBean(NBTaskService.KEY, request);
		TaskFormData taskForm = ts.getTaskFormDataByTaskId(taskId);
		List<FormProperty> formProperties = taskForm.getFormProperties();
		
		Map variables = new HashMap();
		for(FormProperty fp :formProperties) {
			String id = fp.getId();
			String value = request.getParameter(id);
			variables.put(id, value);
		}

		String approved = request.getParameter("approved");
		if(approved!=null) {
			variables.put("approved", Boolean.valueOf(approved));
			variables.put("comment", request.getParameter("comment"));
		}

		ts.completeTask(taskId, null, variables, ygbh, null);

		return null;
	}


	/**
	 * 我的报销单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward myBxd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=utf-8");
		// response.getWriter().print(ja.toString());
		return null;
	}

}
