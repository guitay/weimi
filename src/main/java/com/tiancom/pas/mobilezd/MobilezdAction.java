package com.tiancom.pas.mobilezd;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tiancom.pas.common.framework.struts.PasBaseAction;
import com.tiancom.pas.mobilew.MobilewService;
import com.tiancom.pas.weixin.wxbd.WxbdAction;

public class MobilezdAction extends PasBaseAction {

	protected Class setClass() {
		return this.getClass();
	}
		
	
	//添加账单信息
	public ActionForward insertZd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ygbh = request.getSession().getAttribute("ygbh").toString();
	    String xmbh = request.getParameter("xmbh");
	    String khbh = request.getParameter("khbh");
		String zdje = request.getParameter("zdje");
		String zdlx = request.getParameter("zdlx");
		String zdsm = request.getParameter("zdsm");
		Date s = new Date(request.getParameter("rq"));
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		String rq = f.format(s);
		MobilezdService serv = (MobilezdService)getBean(MobilezdService.KEY, request);
		
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		if (xmbh != "" && xmbh != null) {
			map.put("xmbh", xmbh);
		}
		if (khbh != "" && khbh != null) {
			map.put("khbh", khbh);
		}
		map.put("zdje", zdje);
		map.put("zdlx", zdlx);
		map.put("zdsm", zdsm);
		map.put("rq", rq);
		serv.insertZd(map);
		return null;
	}
	
	//查询列出未报销账单的所有项目
	public ActionForward selectZdXm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String ygbh = request.getSession().getAttribute("ygbh").toString();
	   MobilezdService serv = (MobilezdService)getBean(MobilezdService.KEY, request);
		 
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		List<Map> ZdXm = serv.selectZdXm(map);
		response.getWriter().print(JSONArray.fromObject(ZdXm).toString());
		return null;
	}	
	
	//查询找出各项目需要报销的总额
	public ActionForward selectZdHeader(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String ygbh = request.getSession().getAttribute("ygbh").toString();
		String xmbh = request.getParameter("xmbh");
		String khbh = request.getParameter("khbh");
		MobilezdService serv = (MobilezdService)getBean(MobilezdService.KEY, request);
		
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		map.put("xmbh", xmbh);
		map.put("khbh", khbh);
		List<Map> ZdHeader = serv.selectZdHeader(map);
		response.getWriter().print(JSONArray.fromObject(ZdHeader).toString());
		return null;
	}	
	
	//各项目的账单明细
	public ActionForward selectZdMx(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String ygbh = request.getSession().getAttribute("ygbh").toString();
		String xmbh = request.getParameter("xmbh");
		String khbh = request.getParameter("khbh");
		MobilezdService serv = (MobilezdService)getBean(MobilezdService.KEY, request);
		
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		map.put("xmbh", xmbh);
		map.put("khbh", khbh);
		List<Map> ZdMx = serv.selectZdMx(map);
		response.getWriter().print(JSONArray.fromObject(ZdMx).toString());
		return null;
	}	
	
	public ActionForward changeZd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MobilezdService serv = (MobilezdService)getBean(MobilezdService.KEY, request);
		String zdbh = request.getParameter("zdbh");
		
		Map map = new HashMap();
		map.put("zdbh", zdbh);
		
		List<Map> Zdxx = serv.selectZdByZdbh(map);
		request.setAttribute("Zdxx",Zdxx);
		return mapping.findForward("zdbj");
	}
	
	//修改账单信息
	public ActionForward updateZd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String zdbh = request.getParameter("zdbh");
		String zdje = request.getParameter("zdje");
		String zdlx = request.getParameter("zdlx");
		String zdsm = request.getParameter("zdsm");
		String rq = request.getParameter("rq");
		MobilezdService serv = (MobilezdService)getBean(MobilezdService.KEY, request);
		
		Map map = new HashMap();
		map.put("zdbh", zdbh);
		map.put("zdje", zdje);
		map.put("zdlx", zdlx);
		map.put("zdsm", zdsm);
		map.put("rq", rq);
		serv.updateZd(map);
		return null;
	}
	
	public ActionForward deleteZd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobilezdService serv = (MobilezdService)getBean(MobilezdService.KEY, request);
		String zdbh = request.getParameter("zdbh");
		Map map = new HashMap();
		map.put("zdbh", zdbh);
		serv.deleteZd(map);
		return null;
	}
	public ActionForward toBx(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MobilezdService serv = (MobilezdService)getBean(MobilezdService.KEY, request);
		String ygbh = request.getSession().getAttribute("ygbh").toString();
		String xmbh = request.getParameter("xmbh");
		String khbh = request.getParameter("khbh");
		String xmjc = new String(request.getParameter("xmjc").getBytes("iso-8859-1"), "utf-8"); 
		
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		map.put("xmbh", xmbh);
		map.put("khbh", khbh);
		
		List<Map> ZdMxhz = serv.selectZdMxhz(map);
		List<Map> BmList = serv.selectBm(map);
		request.setAttribute("ZdMxhz",ZdMxhz);
		request.setAttribute("BmList",BmList);
		request.setAttribute("xmbh",xmbh);
		request.setAttribute("khbh",khbh);
		request.setAttribute("xmjc",xmjc);
		request.setAttribute("zje",request.getParameter("zje"));
		request.setAttribute("qsrq",request.getParameter("qsrq"));
		request.setAttribute("jsrq",request.getParameter("jsrq"));
		return mapping.findForward("bx");
	}
	
	//添加报销单信息
		public ActionForward insertBxd(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String ygbh = request.getSession().getAttribute("ygbh").toString();
		    String xmbh = request.getParameter("xmbh");
		    String khbh = request.getParameter("khbh");
		    String bxje = request.getParameter("bxje");
		    String bxbm = request.getParameter("bxbm");
		    String qsrq = request.getParameter("qsrq");
			String jsrq = request.getParameter("jsrq");
			String ccts = request.getParameter("ccts");
			MobilezdService serv = (MobilezdService)getBean(MobilezdService.KEY, request);
			
			Map map = new HashMap();
			map.put("ygbh", ygbh);
			if (xmbh != "" && xmbh != null) {
				map.put("xmbh", xmbh);
			}
			if (khbh != "" && khbh != null) {
				map.put("khbh", khbh);
			}
			map.put("bxbm", bxbm);
			map.put("bxje", bxje);
			map.put("qsrq", qsrq);
			map.put("jsrq", jsrq);
			map.put("ccts", ccts);
			
			serv.insertBxd(map);
			return null;
		}
		
		
		
}
