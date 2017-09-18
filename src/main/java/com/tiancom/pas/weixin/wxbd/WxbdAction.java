/**
 * 
 */
package com.tiancom.pas.weixin.wxbd;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tiancom.pas.common.framework.struts.PasBaseAction;
import com.tiancom.pas.common.util.MD5Digest;

/**
 * 微信绑定
 * 
 * @author chenwx
 * 
 */
//kuang.chun.hua 20170210
public class WxbdAction extends PasBaseAction {

	@Override
	protected Class setClass() {
		return WxbdAction.class;
	}
	

	// 微信绑定
	public ActionForward toPage(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		WxbdForm myfrom = (WxbdForm) form;
		String openid = (String)request.getSession().getAttribute("openid");
		String wxh = (String)request.getSession().getAttribute("wxh");
		String wxmc = (String)request.getSession().getAttribute("wxmc");
		myfrom.setOpenid(openid);
		myfrom.setWxh(wxh);
		myfrom.setWxmc(wxmc);
		
		return mapping.findForward("list");
	}
	
	// 微信绑定
	public ActionForward wxBd(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		WxbdService serv = (WxbdService) getBean(WxbdService.KEY, request);
		
		String openid = (String)request.getSession().getAttribute("openid");
		String wxh = (String)request.getSession().getAttribute("wxh");
		String wxmc = (String)request.getSession().getAttribute("wxmc");
		wxmc = filterEmoji(wxmc);//过滤emoji表情 add chenhm 20170301
		String ygbh = (String)request.getParameter("ygbh");
		String dlmm = (String)request.getParameter("dlmm");

		int ygYgid = serv.queryBdYgXxById(ygbh);
		if (ygYgid <= 0) {
			response.getWriter().print("不存在该用户");
			return null;
		}
		MD5Digest md5 = new MD5Digest();
		Map map = new HashMap();
		map.put("ygbh",ygbh);
		map.put("dlmm",md5.digest(dlmm));
		int ygDlmm = serv.queryBdYgXxByMm(map);
		if (ygDlmm <= 0) {
			response.getWriter().print("密码错误");
			return null;
		} 
		
		
		map = new HashMap();
		map.put("openid",openid);
		map.put("wxh",wxh);
		map.put("wxmc",wxmc.replace("'", "''"));//微信名字有单引号，会导致sql报错   modify chenhm 20170301
		map.put("ygbh",ygbh);
		serv.update_WxbdXx(map);
		String rzms = "微信用户："+wxmc+",绑定了"+ygbh+" 成功";
		//writeLogNoUser(StringUtil.getNowTime("yyyyMMdd"), yhbs, hydh, "03", "1", rzms, request);
		response.getWriter().print("");

		return null;
	}
	
	/**
     * 检测是否有emoji字符
     * @param source 需要判断的字符串
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!notisEmojiCharacter(codePoint)) {
            //判断确认有表情字符
            return true;
            }
        }
        return false;
    }

    /**
     * 非emoji表情字符判断
     * @param codePoint
     * @return
     */
    private static boolean notisEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || 
                (codePoint == 0x9) ||                            
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }
    
	/**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source  需要过滤的字符串
     * @return
     */
    public static String filterEmoji(String source) {
        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }
        
        StringBuilder buf = null;//该buf保存非emoji的字符
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (notisEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            } 
        }
        
        
        if (buf == null) {         
            return "";//如果没有找到非emoji的字符，则返回无内容的字符串
        } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }
}
