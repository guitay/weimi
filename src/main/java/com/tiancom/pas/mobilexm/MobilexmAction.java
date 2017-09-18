package com.tiancom.pas.mobilexm;

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
import com.tiancom.pas.weixin.wxbd.WxbdAction;

public class MobilexmAction extends PasBaseAction {

	protected Class setClass() {
		return this.getClass();
	}
		
	
	//添加项目分享信息
	public ActionForward insertXmfx(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ygbh = request.getSession().getAttribute("ygbh").toString();
	    String xmbh = request.getSession().getAttribute("xmbh").toString();
		String fxnr = request.getParameter("fxnr").replace("\n", "</br>");
		String sftb = request.getParameter("sftb");
		String ztbh = request.getParameter("ztbh");
		String picture1 = request.getParameter("picture1");
		String picture2 = request.getParameter("picture2");
		String picture3 = request.getParameter("picture3");
		MobilexmService serv = (MobilexmService)getBean(MobilexmService.KEY, request);
		
		if(picture1.equals("undefined")){
			picture1="";
		}
		if(picture2.equals("undefined")){
			picture2="";
		}
		if(picture3.equals("undefined")){
			picture3="";
		}
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		map.put("xmbh", xmbh);
		map.put("ztbh", ztbh);
		map.put("fxnr", fxnr);
		map.put("sftb", sftb);
		map.put("picture1", picture1);
		map.put("picture2", picture2);
		map.put("picture3", picture3);
		map.put("fxsj", new Date());
		
		serv.insertXmfx(map);
		return null;
	}
	
	public ActionForward Xmfxfs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String ygbh = request.getParameter("ygbh");
		String jldh = request.getParameter("jldh");
		String fxr = request.getParameter("fxr");
		
		String msg ="";
		MobilexmService serv = (MobilexmService)getBean(MobilexmService.KEY, request);
		
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		map.put("jldh", jldh);
		map.put("fxr", fxr);
		Integer mgs = serv.selectMgsByYgbh(map);
		if (mgs>0) {
			serv.updateYgbhMgs(map);//更新打赏者蜜罐数
			serv.updateFxrMgs(map);//更新受赏者蜜罐数
			serv.insertFsjl(map);//添加打赏记录
			msg=serv.selectSmrByJldh(map);
		}else {
			msg="err";
		}
		
		
		response.getWriter().print(msg);
		return null;
	}	
	
	public ActionForward toFc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MobilexmService serv = (MobilexmService)getBean(MobilexmService.KEY, request);
		String ygbh = request.getParameter("ygbh");
		
		Map map = new HashMap();
		map.put("ygbh", ygbh);
		
		List<Map> fcfxnr = serv.selectFcfxnr(map);
		//List<Map> plxq = serv.selectLbPlxq(map);
		
		//request.setAttribute("plxq",plxq);
		//request.setAttribute("fcfxnr",fcfxnr);
		request.setAttribute("fcCount",fcfxnr.size());
		request.getSession().setAttribute("xmbh","9999");//上传图片后的文件目录用到
		return mapping.findForward("fc");
	}
	
	//添加项目分享信息
		public ActionForward insertFcfx(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String ygbh = request.getSession().getAttribute("ygbh").toString();
		    String fxnr = request.getParameter("fxnr").replace("\n", "</br>");
		    String ztbh = request.getParameter("ztbh");
		    String picture1 = request.getParameter("picture1");
			String picture2 = request.getParameter("picture2");
			String picture3 = request.getParameter("picture3");
			MobilexmService serv = (MobilexmService)getBean(MobilexmService.KEY, request);
			
			if(picture1.equals("undefined")){
				picture1="";
			}
			if(picture2.equals("undefined")){
				picture2="";
			}
			if(picture3.equals("undefined")){
				picture3="";
			}
			Map map = new HashMap();
			map.put("ygbh", ygbh);
			map.put("xmbh", "");
			map.put("ztbh", ztbh);
			map.put("fxnr", fxnr);
			map.put("sftb", "Y");
			map.put("picture1", picture1);
			map.put("picture2", picture2);
			map.put("picture3", picture3);
			map.put("fxsj", new Date());
			
			serv.insertXmfx(map);
			return null;
		}
		
		
		public ActionForward toFxxq(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			
			MobilexmService serv = (MobilexmService)getBean(MobilexmService.KEY, request);
			String jlbh = request.getParameter("jldh");
			
			Map map = new HashMap();
			map.put("jldh", jlbh);
			
			List<Map> fxxq = serv.selectFxxq(map);
			List<Map> plxq = serv.selectPlxq(map);
			
			request.setAttribute("fxxq",fxxq.get(0));
			request.setAttribute("pls",plxq.size());
			request.setAttribute("plxq",plxq);
			request.setAttribute("qy",request.getParameter("qy"));//分享详情页的返回按钮用到，传入前一页的值
			return mapping.findForward("fxxq");
		}
		
		public ActionForward insertFxpl(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String ygbh = request.getSession().getAttribute("ygbh").toString();
			String plnr = request.getParameter("plnr");
			String fxjldh = request.getParameter("fxjldh");
			MobilexmService serv = (MobilexmService)getBean(MobilexmService.KEY, request);
			
			Map map = new HashMap();
			map.put("plr", ygbh);
			map.put("plnr", plnr);
			map.put("fxjldh",fxjldh);
			map.put("plsj", new Date());
			
			serv.insertFxpl(map);
			return null;
		}		

		public ActionForward deleteFxjl(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String jldh = request.getParameter("jldh");
			String ygbh = request.getSession().getAttribute("ygbh").toString();
			MobilexmService serv = (MobilexmService)getBean(MobilexmService.KEY, request);
			
			Map map = new HashMap();
			map.put("jldh", jldh);
			map.put("ygbh", ygbh);
			/*
			String fileName = "http://192.168.191.1:8080/erp/uploadfiles/10739/204/20170525-1729111.png";
			File file = new File(fileName);
			file.delete();*/
			serv.deleteFxjl(map);
			return null;
		}
		
		public ActionForward deletePljl(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				 {
			String jldh = request.getParameter("jldh");
			
			MobilexmService serv = (MobilexmService)getBean(MobilexmService.KEY, request);
			
			Map map = new HashMap();
			map.put("jldh", jldh);
			
			serv.deletePljl(map);
			return null;
		}
		
		public ActionForward loadZt(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String ygbh = request.getSession().getAttribute("ygbh").toString();
			MobilexmService serv = (MobilexmService)getBean(MobilexmService.KEY, request);
			
			Map map = new HashMap();
			map.put("ygbh", ygbh);
			
			List Ztlist = serv.selectZtlist(map);
			JSONArray zjxxjson = JSONArray.fromObject(Ztlist);
			response.getWriter().print(zjxxjson.toString());
			return null;
		}
		
		public ActionForward insertZt(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String ygbh = request.getSession().getAttribute("ygbh").toString();
			String ztnr = request.getParameter("ztnr");
			MobilexmService serv = (MobilexmService)getBean(MobilexmService.KEY, request);
			
			Map map = new HashMap();
			map.put("ygbh", ygbh);
			map.put("ztnr", ztnr);
			String ztbh = serv.insertZt(map);
			response.getWriter().print(ztbh);
			return null;
		}			
		
		
		public ActionForward reloadFclb(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String jldh = request.getParameter("jldh");
			MobilexmService serv = (MobilexmService)getBean(MobilexmService.KEY, request);
			
			Map map = new HashMap();
			map.put("jldh", jldh);
			
			List Fclist = serv.selectFclist(map);
			JSONArray zjxxjson = JSONArray.fromObject(Fclist);
			response.getWriter().print(zjxxjson.toString());
			return null;
		}
		
		public ActionForward reloadFclbPl(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String jldh = request.getParameter("jldh");
			MobilexmService serv = (MobilexmService)getBean(MobilexmService.KEY, request);
			
			Map map = new HashMap();
			map.put("jldh", jldh);
			
			List Fclist = serv.selectPlxq(map);
			JSONArray zjxxjson = JSONArray.fromObject(Fclist);
			response.getWriter().print(zjxxjson.toString());
			return null;
		}
}
