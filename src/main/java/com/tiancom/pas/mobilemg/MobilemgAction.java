package com.tiancom.pas.mobilemg;

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
import com.tiancom.pas.mobilecqgl.MobilecqglService;

public class MobilemgAction extends PasBaseAction {

	protected Class setClass() {
		return this.getClass();
	}
		
	//蜜罐页
	public ActionForward toMg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ygbh = request.getSession().getAttribute("ygbh").toString();
		MobilemgService serv = (MobilemgService)getBean(MobilemgService.KEY, request);
		
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		List<Map> ms = serv.selectMcount(map);
		if (ms.size()==0) {
			Map map2 = new HashMap();
			map2.put("ysjmds", 0);
			map2.put("wsjmds", 0);
			map2.put("mgs", 0);
			ms.add(map2);
		}
		request.setAttribute("ms",ms.get(0));
		
		return mapping.findForward("mg");
	}
	
	//更新蜜罐信息
	public ActionForward updateMg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ygbh = request.getParameter("ygbh");
		String wsjmds = request.getParameter("wsjmds");
		String ysjmds = request.getParameter("ysjmds");
		String mgs = request.getParameter("mgs");
		MobilemgService serv = (MobilemgService)getBean(MobilemgService.KEY, request);
		
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		map.put("wsjmds", wsjmds);
		map.put("ysjmds", ysjmds);
		map.put("mgs", mgs);
		serv.updateMg(map);
		
		return null;
	}
	
	//中奖信息流
	public ActionForward reloadZJXX(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MobilemgService serv = (MobilemgService)getBean(MobilemgService.KEY, request);
		
		List zjxx = serv.getZjxx();
		JSONArray zjxxjson = JSONArray.fromObject(zjxx);
		response.getWriter().print(zjxxjson.toString());
		return null;
	}
	
	//更新蜜罐信息
	public ActionForward updateMg2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ygbh = request.getParameter("ygbh");
		String mgs = request.getParameter("mgs");
		MobilemgService serv = (MobilemgService)getBean(MobilemgService.KEY, request);
		
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		map.put("mgs", mgs);
		serv.updateMg2(map);
		
		return null;
	}
	
	//更新蜜罐信息
	public ActionForward updateMg3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ygbh = request.getParameter("ygbh");
		String mgs = request.getParameter("mgs");
		MobilemgService serv = (MobilemgService)getBean(MobilemgService.KEY, request);
		
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		map.put("mgs", mgs);
		serv.updateMg3(map);
		
		return null;
	}
	
	//添加中奖记录
	public ActionForward insertZjjl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ygbh = request.getParameter("ygbh");
		String xm = request.getParameter("xm");
		String jpmc = request.getParameter("jpmc");
		MobilemgService serv = (MobilemgService)getBean(MobilemgService.KEY, request);
		
		Date s = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		String strDateTime = f.format(s);
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		map.put("xm", xm);
		map.put("jpmc", jpmc);
		map.put("zjrq", strDateTime);
		
		serv.insertZjjl(map);
		return null;
	}
	
	//添加中奖记录
	public ActionForward selectJplist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ygbh = request.getParameter("ygbh");
		MobilemgService serv = (MobilemgService)getBean(MobilemgService.KEY, request);
		
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		
		List Jplist = serv.selectJplist(map);
		JSONArray zjxxjson = JSONArray.fromObject(Jplist);
		response.getWriter().print(zjxxjson.toString());
		return null;
	}
	
	
	public ActionForward selectMgs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String ygbh = request.getParameter("ygbh");
		MobilemgService serv = (MobilemgService)getBean(MobilemgService.KEY, request);
		
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		List<Map> ms = serv.selectMcount(map);
		if (ms.size()==0) {
			Map map2 = new HashMap();
			map2.put("ysjmds", 0);
			map2.put("wsjmds", 0);
			map2.put("mgs", 0);
			ms.add(map2);
		}
		JSONArray msjson = JSONArray.fromObject(ms);
		response.getWriter().print(msjson.toString());
		return null;
	}	
	
	
}
