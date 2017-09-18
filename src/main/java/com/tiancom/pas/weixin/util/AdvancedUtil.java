package com.tiancom.pas.weixin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tiancom.pas.weixin.pojo.SNSUserInfo;
import com.tiancom.pas.weixin.pojo.WeixinOauth2Token;
import com.tiancom.pas.weixin.pojo.WeixinUserInfo;
import com.tiancom.pas.weixin.response.Article;

/**
 * 高级接口工具类
 * 
 * @author liufeng
 * @date 2013-11-9
 */
public class AdvancedUtil {
	private static Log log = LogFactory.getLog(AdvancedUtil.class);

	
	

	/**
	 * 获取网页授权凭证
	 * 
	 * @param appId 公众账号的唯一标识
	 * @param appSecret 公众账号的密钥
	 * @param code
	 * @return WeixinAouth2Token
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
		WeixinOauth2Token wat = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code&lang=zh_CN";
		
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);
		// 获取网页授权凭证
		//String returnstr =  CommonUtil.httpsRequest(requestUrl, "GET", null);
		//System.out.println("returnstr-->"+returnstr);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			//Map Map2 = (Map)new Gson().fromJson(returnstr, JsonObject.class);
			try {
				wat = new WeixinOauth2Token();
				String openid = jsonObject.get("openid").toString().replaceAll("\"", "");
				String access_token = jsonObject.get("access_token").toString().replaceAll("\"", "");
				String expires_in = jsonObject.get("expires_in").toString().replaceAll("\"", "");
				String refresh_token = jsonObject.get("refresh_token").toString().replaceAll("\"", "");
				String scope = jsonObject.get("scope").toString().replaceAll("\"", "");

				wat.setAccessToken(access_token);
				wat.setExpiresIn(Integer.parseInt(expires_in));
				wat.setRefreshToken(refresh_token);
				wat.setOpenId(openid);
				wat.setScope(scope);
			} catch (Exception e) {
				wat = null;
				String error="获取token失败 errcode:"+jsonObject.get("errcode").toString().replaceAll("\"", "") + " errmsg:"+jsonObject.get("errmsg").toString().replaceAll("\"", ""); 
				log.error(error);
			}
		}
		return wat;
	}

	/**
	 * 刷新网页授权凭证
	 * 
	 * @param appId 公众账号的唯一标识
	 * @param refreshToken
	 * @return WeixinAouth2Token
	 */
	public static WeixinOauth2Token refreshOauth2AccessToken(String appId, String refreshToken) {
		WeixinOauth2Token wat = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		//String requestUrl = "https://tiancom.sinaapp.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);
		// 刷新网页授权凭证
		//String returnstr = CommonUtil.httpsRequest(requestUrl, "GET", null);
		//Map Map2 = (Map)new Gson().fromJson(returnstr, new TypeToken<Map<String, Object>>(){}.getType());
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			//Map Map2 = (Map)new Gson().fromJson(returnstr, JsonObject.class);
			try {
				wat = new WeixinOauth2Token();
				String openid = jsonObject.get("openid").toString().replaceAll("\"", "");
				String access_token = jsonObject.get("access_token").toString().replaceAll("\"", "");
				String expires_in = jsonObject.get("expires_in").toString().replaceAll("\"", "");
				String refresh_token = jsonObject.get("refresh_token").toString().replaceAll("\"", "");
				String scope = jsonObject.get("scope").toString().replaceAll("\"", "");
				
				wat.setAccessToken(access_token);
				wat.setExpiresIn(Integer.parseInt(expires_in));
				wat.setRefreshToken(refresh_token);
				wat.setOpenId(openid);
				wat.setScope(scope);
			} catch (Exception e) {
				wat = null;
				int errorCode = Integer.parseInt(jsonObject.get("errcode").toString().replaceAll("\"", ""));
				String errorMsg = jsonObject.get("errmsg").toString().replaceAll("\"", "");
				log.error("刷新网页授权凭证失败 errcode:{} errmsg:{}"+errorCode+ errorMsg);
			}
		}
		return wat;
	}

	/**
	 * 通过网页授权获取用户信息
	 * 
	 * @param accessToken 网页授权接口调用凭证
	 * @param openId 用户标识
	 * @return SNSUserInfo
	 */
	@SuppressWarnings( { "deprecation", "unchecked" })
	public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
		SNSUserInfo snsUserInfo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 通过网页授权获取用户信息
		//String returnstr = CommonUtil.httpsRequest(requestUrl, "GET", null);
		//Map Map2 = (Map)new Gson().fromJson(returnstr, new TypeToken<Map<String, Object>>(){}.getType());
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {

		//if (null != returnstr) {
			try {
				snsUserInfo = new SNSUserInfo();

				String openid = jsonObject.get("openid").toString().replaceAll("\"", "");
				String nickname = jsonObject.get("nickname").toString().replaceAll("\"", "");
				String sex = jsonObject.get("sex").toString().replaceAll("\"", "");
				String country = jsonObject.get("country").toString().replaceAll("\"", "");
				String province = jsonObject.get("province").toString().replaceAll("\"", "");
				String city = jsonObject.get("city").toString().replaceAll("\"", "");
				String headimgurl = jsonObject.get("headimgurl").toString().replaceAll("\"", "");
				
				// 用户的标识
				snsUserInfo.setOpenId(openid);
				// 昵称
				snsUserInfo.setNickname(nickname);
				// 性别（1是男性，2是女性，0是未知）
				snsUserInfo.setSex(Integer.parseInt(sex));
				// 用户所在国家
				snsUserInfo.setCountry(country);
				// 用户所在省份
				snsUserInfo.setProvince(province);
				// 用户所在城市
				snsUserInfo.setCity(city);
				// 用户头像
				snsUserInfo.setHeadImgUrl(headimgurl);
				// 用户特权信息
				//snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
			} catch (Exception e) {
				snsUserInfo = null;
				int errorCode = Integer.parseInt(jsonObject.get("errcode").toString().replaceAll("\"", ""));
				String errorMsg = jsonObject.get("errmsg").toString().replaceAll("\"", "");
				log.error("获取用户信息失败 errcode:{} errmsg:{}"+errorCode+errorMsg);
			}
		}
		return snsUserInfo;
	}

	
	/**
	 * 获取用户信息
	 * 
	 * @param accessToken 接口访问凭证
	 * @param openId 用户标识
	 * @return WeixinUserInfo
	 */
	public static WeixinUserInfo getUserInfo(String accessToken, String openId) {
		WeixinUserInfo weixinUserInfo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 获取用户信息
		//String returnstr = CommonUtil.httpsRequest(requestUrl, "GET", null);
		//Map Map2 = (Map)new Gson().fromJson(returnstr, new TypeToken<Map<String, Object>>(){}.getType());
		//JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {

		//if (null != returnstr) {
			try {
				String openid = jsonObject.get("openid").toString().replaceAll("\"", "");
				String subscribe = jsonObject.get("subscribe").toString().replaceAll("\"", "");
				String subscribe_time = jsonObject.get("subscribe_time").toString().replaceAll("\"", "");
				String nickname = jsonObject.get("nickname").toString().replaceAll("\"", "");
				String sex = jsonObject.get("sex").toString().replaceAll("\"", "");
				String country = jsonObject.get("country").toString().replaceAll("\"", "");
				String province = jsonObject.get("province").toString().replaceAll("\"", "");
				String city = jsonObject.get("city").toString().replaceAll("\"", "");
				String language = jsonObject.get("language").toString().replaceAll("\"", "");
				String headimgurl = jsonObject.get("headimgurl").toString().replaceAll("\"", "");
				
				weixinUserInfo = new WeixinUserInfo();
				// 用户的标识
				weixinUserInfo.setOpenId(openid);
				// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				weixinUserInfo.setSubscribe(Integer.parseInt(subscribe));
				// 用户关注时间
				weixinUserInfo.setSubscribeTime(subscribe_time);
				// 昵称
				weixinUserInfo.setNickname(nickname);
				// 用户的性别（1是男性，2是女性，0是未知）
				weixinUserInfo.setSex(Integer.parseInt(sex));
				// 用户所在国家
				weixinUserInfo.setCountry(country);
				// 用户所在省份
				weixinUserInfo.setProvince(province);
				// 用户所在城市
				weixinUserInfo.setCity(city);
				// 用户的语言，简体中文为zh_CN
				weixinUserInfo.setLanguage(language);
				// 用户头像
				weixinUserInfo.setHeadImgUrl(headimgurl);
			} catch (Exception e) {
				if (0 == weixinUserInfo.getSubscribe()) {
					log.error("用户{}已取消关注"+ weixinUserInfo.getOpenId());
				} else {
					//int errorCode =  Map2.get("errcode").toString();
					String errorMsg = jsonObject.get("errmsg").toString().replaceAll("\"", "");
					log.error("获取用户信息失败 errcode:{} errmsg:{}"+ errorMsg);
				}
			}
		}
		return weixinUserInfo;
	}

	
	
	public static void main(String args[]) {
		// 获取接口访问凭证
		String accessToken = CommonUtil.getToken("APPID", "APPSECRET").getAccessToken();


		/**
		 * 发送客服消息（图文消息）
		 */
		Article article1 = new Article();
		article1.setTitle("微信上也能斗地主");
		article1.setDescription("");
		article1.setPicUrl("http://www.egouji.com/xiaoq/game/doudizhu_big.png");
		article1.setUrl("http://resource.duopao.com/duopao/games/small_games/weixingame/Doudizhu/doudizhu.htm");
		Article article2 = new Article();
		article2.setTitle("傲气雄鹰\n80后不得不玩的经典游戏");
		article2.setDescription("");
		article2.setPicUrl("http://www.egouji.com/xiaoq/game/aoqixiongying.png");
		article2.setUrl("http://resource.duopao.com/duopao/games/small_games/weixingame/Plane/aoqixiongying.html");
		List<Article> list = new ArrayList<Article>();
		list.add(article1);
		list.add(article2);




		/**
		 * 获取用户信息
		 */
		WeixinUserInfo user = getUserInfo(accessToken, "oEdzejiHCDqafJbz4WNJtWTMbDcE");
		System.out.println("OpenID：" + user.getOpenId());
		System.out.println("关注状态：" + user.getSubscribe());
		System.out.println("关注时间：" + user.getSubscribeTime());
		System.out.println("昵称：" + user.getNickname());
		System.out.println("性别：" + user.getSex());
		System.out.println("国家：" + user.getCountry());
		System.out.println("省份：" + user.getProvince());
		System.out.println("城市：" + user.getCity());
		System.out.println("语言：" + user.getLanguage());
		System.out.println("头像：" + user.getHeadImgUrl());

	
	}
}
