package com.tiancom.pas.weixin.servlet;



import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tiancom.pas.weixin.pojo.SNSUserInfo;
import com.tiancom.pas.weixin.pojo.WeixinOauth2Token;
import com.tiancom.pas.weixin.util.AdvancedUtil;

/**
 * 授权后的回调请求处理
 * 
 * @author liufeng
 * @date 2013-11-12
 */
// kuang.chun.hua 20170210
public class OAuthServlet extends HttpServlet {
	private static final long serialVersionUID = -1847238807216447030L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setCharacterEncoding("gb2312");
		//response.setCharacterEncoding("gb2312");
		request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");

		// 用户同意授权后，能获取到code
		String chm = request.getParameter("state");
		String code = request.getParameter("code");
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("weixin.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String appId = properties.getProperty("appId");
		String appSecret = properties.getProperty("appSecret");

		// 用户同意授权
		if (!"authdeny".equals(code)) {
			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(appId, appSecret, code);
			// 网页授权接口访问凭证
			String accessToken = weixinOauth2Token.getAccessToken();
			// 用户标识
			String openId = weixinOauth2Token.getOpenId();
			// 获取用户信息
			SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);

			// 设置要传递的参数
			request.getSession().setAttribute("snsUserInfo", snsUserInfo);
			request.getSession().setAttribute("openid", snsUserInfo.getOpenId());
			request.getSession().setAttribute("wxh", snsUserInfo.getOpenId());
			request.getSession().setAttribute("wxmc", snsUserInfo.getNickname());
			request.getSession().setAttribute("wxtx", snsUserInfo.getHeadImgUrl());
			request.getSession().setAttribute("xb", snsUserInfo.getSex());
			request.getSession().setAttribute("sf", snsUserInfo.getProvince());
			request.getSession().setAttribute("cs", snsUserInfo.getCity());
			request.getSession().setAttribute("stateUrl", chm);
		}
		// 跳转到index.jsp
		request.getRequestDispatcher("/m/login2.jsp").forward(request, response);
	}
}
