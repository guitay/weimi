package com.tiancom.pas.qa;

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

import com.google.gson.Gson;
import com.tiancom.pas.common.framework.struts.PasBaseAction;
import com.tiancom.pas.common.json.JsonUtil;
import com.tiancom.pas.common.result.ResultCommon;
import com.tiancom.pas.common.util.Grid.easyui.PageUtil;
import com.tiancom.pas.lucene.LucService;
import com.tiancom.pas.lucene.bean.SearchResult;

public class QAAction extends PasBaseAction {

	protected Class setClass() {
		return this.getClass();
	}

	public ActionForward toEditPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QAService serv = (QAService) getBean(QAService.KEY, request);
		// String wtid = request.getParameter("wtid");
		// String taskId = request.getParameter("taskId");
		//
		// QA qa = serv.queryByKey(Integer.valueOf(wtid)).getRow();
		//
		// Map map = new HashMap();
		// map.put("qa", qa);

		return mapping.findForward("edit");
	}

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QAService serv = (QAService) getBean(QAService.KEY, request);
		String wtid = request.getParameter("wtid");
		QA qa = serv.queryByKey(Integer.valueOf(wtid)).getRow();
		request.setAttribute("qa", qa);

		return mapping.findForward("edit");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QAService serv = (QAService) getBean(QAService.KEY, request);
		String bxdh = request.getParameter("bxdh");
		String taskId = request.getParameter("taskId");
		Map map = new HashMap();
		map.put("bxdh", bxdh);
		map.put("taskId", taskId);
		// serv.deleteBxd(map);
		return null;
	}

	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QAService serv = (QAService) getBean(QAService.KEY, request);
		List<QA> list = serv.queryByCondition(null, null).getRows();
		PageUtil page = new PageUtil(request);
		int count = list.size();// ((Integer) rc.getRows().get("count")).intValue();
		JSONObject object = new JSONObject();
		object.put("total", count);
		if (count > 0) {
			// List<Jzd> datas = ts.queryByCondition(jzdh, xmbh, bmid, ygid,
			// page.getStartRows(), page.getRows()).getRows();
			JSONArray a = JsonUtil.covertListToJsonArray("qa", list);
			object.put("rows", a);
		} else {
			object.put("rows", new JSONArray());
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(object.toString());
		response.addHeader("X-JSON", "true");
		return null;
	}
	
	public ActionForward toSearchPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("search");
	}
	/**
	 * 全文检索
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LucService serv = (LucService) getBean(LucService.KEY, request);
		String keyword = request.getParameter("keyword");
		String[] fields = {"bzwt","dnda"};
		List<SearchResult> list = serv.search(keyword, fields, 10);

		Gson g = new Gson();
		String ja = g.toJson(list);
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(ja.toString());
		response.addHeader("X-JSON", "true");
		return null;
	} 
	
	/**
	 * 显示问题
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showWt(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QAService serv = (QAService) getBean(QAService.KEY, request);
		String wtid = request.getParameter("wtid");
		
		QA qa = serv.queryByKey(Integer.valueOf(wtid)).getRow();
		request.setAttribute("qa", qa);
		
		return mapping.findForward("show");
	} 

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QAService serv = (QAService) getBean(QAService.KEY, request);
		String wtid = request.getParameter("wtid");
		String flid = request.getParameter("flid");
		String bzwt = request.getParameter("bzwt");
		String dnda = request.getParameter("dnda");
		String dwda = request.getParameter("dwda");
		String wjm = request.getParameter("wjm");

		QA qa = new QA();
		qa.setWtid(wtid == null ? null : new Integer(wtid));
		qa.setFlid(flid == null ? null : new Integer(flid));
		qa.setBzwt(bzwt);
		qa.setDnda(dnda);
		qa.setDwda(dwda);
		qa.setWjm(wjm);

		ResultCommon rc = serv.saveQA(qa);
		JSONObject jo = new JSONObject();
		jo.put("code", rc.getCode());
		jo.put("desc", rc.getDesc());
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(jo.toString());
		response.addHeader("X-JSON", "true");

		return null;
	}
	public ActionForward rebuildAllIndexes(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QAService qa = (QAService)this.getBean(QAService.KEY, request);
		qa.rebuildAllIndex();
		return null;
	}
	

}
