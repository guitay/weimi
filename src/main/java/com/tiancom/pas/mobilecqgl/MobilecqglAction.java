package com.tiancom.pas.mobilecqgl;

import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.tiancom.pas.common.framework.context.LoginUser;
import com.tiancom.pas.common.framework.spring.SpringAppContext;
import com.tiancom.pas.common.framework.struts.PasBaseAction;
import com.tiancom.pas.common.util.DateUtil;
import com.tiancom.pas.common.util.PasException;
import com.tiancom.pas.khsearch.KhSearchService;
import com.tiancom.pas.kqgl.KqglForm;
import com.tiancom.pas.kqgl.KqglService;
import com.tiancom.pas.kqgl.ReBdFymx;
import com.tiancom.pas.kqgl.ReFymxjt;
import com.tiancom.pas.kqgl.ReFymxqt;
import com.tiancom.pas.kqgl.ReFymxsd;
import com.tiancom.pas.kqgl.ReFymxzd;
import com.tiancom.pas.kqgl.ReFymxzs;
import com.tiancom.pas.kqgl.ReYgkqjl;
import com.tiancom.pas.kqgl.ReYgkqnr;
import com.tiancom.pas.kqgl.YgkqXmjl;
import com.tiancom.pas.mobilelogin.MobileLoginService;
import com.tiancom.pas.mobilelogin.Rsglygxx;

public class MobilecqglAction extends PasBaseAction {

	protected Class setClass() {
		return this.getClass();
	}
	
	public ActionForward toYgqd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobileLoginService loginServ = (MobileLoginService) getBean(
				MobileLoginService.key, request);
		String ygbh = request.getParameter("ygbh");
		Rsglygxx yg = loginServ.selectByDlmc(ygbh);
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
		String sysDate = f.format(new Date());
		
		MobilecqglService serv2 = (MobilecqglService)getBean(MobilecqglService.KEY, request);
		
		Map map = new HashMap();
		
		map.put("kqrq", sysDate);
		
		map.put("ygid", yg.getYgid());
		
		List<Map> xmmcList = serv2.selectXmList(map);
		List<Map> sfList = serv2.selectSfList(map);
		
		KhSearchService kh = (KhSearchService) SpringAppContext.getBean(KhSearchService.KEY, request);
		List<Map> khList = kh.loadKh("");
		
		request.getSession().setAttribute("sfMap",sfList);
		request.getSession().setAttribute("xmmcMap",xmmcList);
		request.getSession().setAttribute("khMap",khList);
		request.getSession().setAttribute("kqrq",sysDate);
		request.getSession().setAttribute("ygbh",ygbh);

		request.getSession().setAttribute("yg",yg);
		//if (request.getParameter("first").toString().equals("1")) {

			return mapping.findForward("index");
		/*}else {
			return mapping.findForward("ygqd");
		}*/
		
	}
	
	//保存考勤记录
		public ActionForward saveYgqd(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			MobilecqglService serv = (MobilecqglService)getBean(MobilecqglService.KEY, request);
			Map map = new HashMap();
			
			Date s = new Date(request.getParameter("qdrq"));
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			String strDateTime = f.format(s);
			String xmbh = request.getParameter("xmbh");
			String khbh = request.getParameter("khbh");
			
			map.put("ygid", request.getParameter("ygid"));
			if (xmbh != "" && xmbh != null) {
				map.put("xmbh", xmbh);
			}
			if (khbh != "" && khbh != null) {
				map.put("khbh", khbh);
			}
			
			map.put("gzsj", request.getParameter("gzsj"));
			map.put("qdrq", strDateTime);
			map.put("qdsj", new Date());
			map.put("ygbh", request.getSession().getAttribute("ygbh"));
			map.put("jlnr", request.getParameter("jlnr"));
			String msg=serv.saveYgqd(map);
			response.setContentType("text/html;charset=gb2312");
			response.getWriter().print(msg);
			return null;
		}
		
		public ActionForward chackQdls(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			MobilecqglService serv = (MobilecqglService)getBean(MobilecqglService.KEY, request);
			Map map = new HashMap();
			Date s = new Date(request.getParameter("qdrq"));
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			String strDateTime = f.format(s);
			String xmbh = request.getParameter("xmbh");
			String khbh = request.getParameter("khbh");
			
			map.put("ygid", request.getParameter("ygid"));
			if (xmbh != "" && xmbh != null) {
				map.put("xmbh", xmbh);
			}
			if (khbh != "" && khbh != null) {
				map.put("khbh", khbh);
			}
			map.put("qdrq", strDateTime);
			
			Integer count = serv.selectCount(map);
			
			Integer gzsj = serv.selectGzsj(map);
			if(gzsj==null){gzsj=0;}
			
			response.setContentType("text/html;charset=gb2312");
			response.getWriter().print(count+"-"+gzsj);
			return null;
		}
		
		//点击上天下天 重新拉取最近历史信息
		public ActionForward roadLs(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			MobilecqglService serv2 = (MobilecqglService)getBean(MobilecqglService.KEY, request);
			String ygid = request.getParameter("ygid");
			Map map = new HashMap();
			
			map.put("ygid", ygid);
			
			List<Map> xmjcList = serv2.selectQdls(map);
			net.sf.json.JSONArray wcfwjson = net.sf.json.JSONArray.fromObject(xmjcList);
			response.getWriter().print(wcfwjson.toString());
			return null;
		}
		
		public ActionForward roadZdLs(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			MobilecqglService serv2 = (MobilecqglService)getBean(MobilecqglService.KEY, request);
			String ygbh = request.getSession().getAttribute("ygbh").toString();
			Map map = new HashMap();
			
			map.put("ygbh", ygbh);
			
			List<Map> ZdList = serv2.selectZdls(map);
			net.sf.json.JSONArray wcfwjson = net.sf.json.JSONArray.fromObject(ZdList);
			response.getWriter().print(wcfwjson.toString());
			return null;
		}
		
		public ActionForward roadDpLs(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			MobilecqglService serv2 = (MobilecqglService)getBean(MobilecqglService.KEY, request);
			String ygbh = request.getSession().getAttribute("ygbh").toString();
			Map map = new HashMap();
			
			map.put("ygbh", ygbh);
			
			List<Map> DpList = serv2.selectDpls(map);
			net.sf.json.JSONArray wcfwjson = net.sf.json.JSONArray.fromObject(DpList);
			response.getWriter().print(wcfwjson.toString());
			return null;
		}
	
		
		//历史记录页面
		public ActionForward loadLs(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String ygid = request.getParameter("ygid");
			String kqrq = request.getParameter("kqrq");
			MobilecqglService serv2 = (MobilecqglService)getBean(MobilecqglService.KEY, request);
			Map map = new HashMap();
			
			map.put("ygid", ygid);
			List ls = serv2.selectQdls2(map);
			List ls2 = serv2.selectQdls3(map);
			request.setAttribute("lsjl2",ls2);
			request.setAttribute("lsjl",ls);
			
			
			return mapping.findForward("lsjl");
		}
		
		public ActionForward deleteJl(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String jldh = request.getParameter("jldh");
			String kqrq = request.getParameter("kqrq");
			
			MobilecqglService serv2 = (MobilecqglService)getBean(MobilecqglService.KEY, request);
			Map map = new HashMap();
			
			map.put("jldh", jldh);
			map.put("ygbh", request.getSession().getAttribute("ygbh"));
			serv2.deleteJl(map);
			return null;
		}
		
		//项目列表页
		public ActionForward toXmlb(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String ygbh = request.getParameter("ygbh");
			MobilecqglService serv = (MobilecqglService)getBean(MobilecqglService.KEY, request);
			
			Map map = new HashMap();
			map.put("ygbh", ygbh);
			List<Map> xmlbList = serv.selectXmlbList(map);
			
			request.setAttribute("xmlbList",xmlbList);
			request.setAttribute("xmlbCount",xmlbList.size());
			request.setAttribute("ygbh",ygbh);
			
			return mapping.findForward("xmlb");
		}
		
		//项目详情页
		public ActionForward toXmxq(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String xmbh = request.getParameter("xmbh");
			String ygbh = request.getSession().getAttribute("ygbh").toString();
			MobilecqglService serv = (MobilecqglService)getBean(MobilecqglService.KEY, request);
			
			Map map = new HashMap();
			map.put("xmbh", xmbh);
			map.put("ygbh", ygbh);
			List<Map> xmxqList = serv.selectXmxqList(map);//项目基本情况
			List<Map> xmzj = serv.selectXmzj(map);//项目总监
			List<Map> xmzxs = serv.selectXmzxs(map);//项目咨询师
			List<Map> xmgcs = serv.selectXmgcs(map);//项目工程师
			List<Map> xmzc = serv.selectXmzc(map);//项目支持
			List<Map> xmzbnr = serv.selectXmzbnr(map);//项目周报
			List<Map> xmfxnr = serv.selectXmfxnr(map);//项目分享
			List<Map> jlnr = serv.selectJlnr(map);//项目分享
		
			
			request.setAttribute("xmxqList",xmxqList.get(0));
			request.setAttribute("xmzj",xmzj);
			request.setAttribute("xmzxs",xmzxs);
			request.setAttribute("xmgcs",xmgcs);
			request.setAttribute("xmzc",xmzc);
			request.setAttribute("xmzjcount",xmzj.size());
			request.setAttribute("zxscount",xmzxs.size());
			request.setAttribute("gcscount",xmgcs.size());
			request.setAttribute("zccount",xmzc.size());
			request.setAttribute("count",xmzc.size()+xmzj.size()+xmzxs.size()+xmgcs.size());
			request.setAttribute("xmzbnr",xmzbnr);
			request.setAttribute("xmfxnr",xmfxnr);
			request.setAttribute("jlnr",jlnr);
			
			request.getSession().setAttribute("xmbh",xmbh);
			return mapping.findForward("xmxq");
		}
		
		//更改关注项目状态
		public ActionForward changeSfgz(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String val = request.getParameter("val");
			String ygbh = request.getSession().getAttribute("ygbh").toString();
			String xmbh = request.getSession().getAttribute("xmbh").toString();
			MobilecqglService serv = (MobilecqglService)getBean(MobilecqglService.KEY, request);
			
			Map map = new HashMap();
			map.put("xmbh", xmbh);
			map.put("ygbh", ygbh);
			map.put("czsj", new Date());
			if (val.equals("0")) {
				serv.updateGz(map);
			}else {
				serv.insertGz(map);
			}
			return null;
		}
		
		public ActionForward saveKh(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			KhSearchService serv = (KhSearchService)getBean(KhSearchService.KEY, request);
			String ygbh = request.getSession().getAttribute("ygbh").toString();
			String khmc = request.getParameter("khmc");
			Map map = new HashMap();
			
			map.put("ygbh", ygbh);
			map.put("khmc", khmc);
			
			serv.saveKh(map);
			String khbh = serv.selectKhbhBykKhmc(map);
			response.getWriter().print(khbh);
			return null;
		}
}
