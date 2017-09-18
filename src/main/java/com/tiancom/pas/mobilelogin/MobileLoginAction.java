package com.tiancom.pas.mobilelogin;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.ehcache.Cache;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
import com.tiancom.pas.common.framework.context.LoginUser;
import com.tiancom.pas.common.framework.context.SessionContainer;
import com.tiancom.pas.common.framework.spring.SpringAppContext;
import com.tiancom.pas.common.framework.struts.PasBaseAction;
import com.tiancom.pas.common.framework.struts.SessionBean;
import com.tiancom.pas.common.log.entity.XtbYyrz;
import com.tiancom.pas.common.log.service.YyrzService;
import com.tiancom.pas.common.tree.entity.XtbXtcd;
import com.tiancom.pas.common.tree.service.XtcdService;
import com.tiancom.pas.common.util.DateUtil;
import com.tiancom.pas.common.util.IConst;
import com.tiancom.pas.common.util.MD5Digest;
import com.tiancom.pas.khsearch.KhSearchService;
import com.tiancom.pas.kqgl.KqglService;
import com.tiancom.pas.login.XtbAqjs;
import com.tiancom.pas.login.XtbXtcs;
import com.tiancom.pas.mobilecqgl.MobilecqglService;
import com.tiancom.pas.weixin.pojo.M_WXBD;
import com.tiancom.pas.weixin.wxbd.WxbdService;

public class MobileLoginAction extends PasBaseAction {

	@Override
	protected Class<MobileLoginAction> setClass() {
		// TODO Auto-generated method stub
		return MobileLoginAction.class;
	}

	// 登录
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MobileLoginService loginServ = (MobileLoginService) getBean(
				MobileLoginService.key, request);
		MobileLoginForm loginForm = (MobileLoginForm) form;
		String username = loginForm.getUsername();
		String password = loginForm.getPassword();
		Rsglygxx yg = loginServ.selectByDlmc(username);

		if (null == yg) {// 不存在该用户
			addErrors(request, "noUser", "不存在该用户");

			return mapping.findForward("login");

		} else if (yg.getYgzt() == 2) {
			addErrors(request, "noUser", "该用户已注销");
			return mapping.findForward("login");

		} else {
			// 存在用户,加密用户输入的密码
			MD5Digest md5 = new MD5Digest();
			String mypsw = md5.digest(password);
			// 取出数据库中的密码

			String psw = yg.getDlmm().trim();
			if (!psw.equals(mypsw)) {// 密码错误
				addErrors(request, "noUser", "您输入的员工工号或密码有误!");
				return mapping.findForward("login");

			} else {
				request.getSession().setAttribute("yg", yg);
				
				SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
				String sysDate = f.format(new Date());
				KqglService serv = (KqglService)getBean(KqglService.KEY, request);
				MobilecqglService serv2 = (MobilecqglService)getBean(MobilecqglService.KEY, request);
				
				Map map = new HashMap();
				
				map.put("kqrq", sysDate);
				map.put("ygid", yg.getYgid());
				List<Map> xmmcList = serv2.selectXmList(map);
				List<Map> sfList = serv2.selectSfList(map);
				List<Map> xmjcList = serv2.selectQdls(map);
				request.getSession().setAttribute("sfMap",sfList);

				KhSearchService kh = (KhSearchService) SpringAppContext.getBean(KhSearchService.KEY, request);
				List<String> khList = kh.loadKh("");
				request.getSession().setAttribute("khMap",khList);
				request.getSession().setAttribute("xmmcMap",xmmcList);
				request.getSession().setAttribute("xmjcMap",xmjcList);
				request.getSession().setAttribute("kqrq",sysDate);
				request.getSession().setAttribute("ygbh",yg.getDlmc());
				
				return mapping.findForward("index");
			}

		}

	}
	
//	 登录
	// kuang.chun.hua 20170210 start
	public ActionForward login2(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		WxbdService serv = (WxbdService)  SpringAppContext.getBean(WxbdService.KEY, request);
		String openid = (String)request.getSession().getAttribute("openid");
		String stateUrl = (String)request.getSession().getAttribute("stateUrl");
		
		if (null == openid){
			addErrors(request, "noUser", "请重新登录");
			return mapping.findForward("login");
		}else{
	 		request.setAttribute("openid", openid);
			// 判断ID是否已绑定
		 	M_WXBD m = serv.queryBdWxbd(openid);
		 	if(m==null){
				//查询没绑定，绑定微信号
				return mapping.findForward("wxbd");
			}else{
				//已绑定，跳转登记
				if(!"STATE".equals(stateUrl)){
					//return mapping.findForward("mobilecqgltopage");
					request.getRequestDispatcher("../paramoper/mobilexm.do?method=toFxxq&"+stateUrl+"&Ajax="+1).forward(request, response);
					return null;
				}
				//return mapping.findForward("mobilecqgltopage");
				request.getRequestDispatcher("../paramoper/mobilecqgl.do?method=toYgqd&first=1&ygbh="+m.getYgbh()+"&Ajax="+1).forward(request, response);
				return null;
			}
		}
	}
	// kuang.chun.hua 20170210 end
}
