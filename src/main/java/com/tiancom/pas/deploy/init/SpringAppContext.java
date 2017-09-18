/**
 * 
 */
package com.tiancom.pas.deploy.init;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * com.tiancom.pas.framework.spring
 * <br>
 * 说明：<br>
 * <br>
 * @author: meteor
 * 创建时间 2007-3-19 下午03:43:55
 */
public class SpringAppContext {
	/**
	 * Holds singleton instance
	 */
	private static ApplicationContext instance;
	private static String[] minorConfig={ 
		"/applicationContext_resources.xml",
		"/applicationContext_activiti.xml",
		"com/tiancom/pas/common/config/applicationContext_common.xml",
		"com/tiancom/pas/common/config/applicationContext_minor.xml",
		"com/tiancom/pas/activiti/task/applicationContext_task.xml",
		"com/tiancom/pas/deploy/init/applicationContext_initupdate.xml"
		};

	
	/**
	 * 返回spring ClassPathXmlApplicationContext实例
	 * 返回值:ClassPathXmlApplicationContext
	 * @return
	 */
//	static public Object getBean(String beanId){
//		return getInstance().getBean(beanId);
//	}
	
	static public Object getBean(String beanId, HttpServletRequest request){ 
		ApplicationContext app = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext()); 
		return app.getBean(beanId);
	}

	/**
	 * 构造函数
	 */
	private SpringAppContext() {
		// prevent creation
	}

	/**
	 * Returns the singleton instance.
	 * 返回值:ClassPathXmlApplicationContext
	 * @return
	 */
	static public ApplicationContext getInstance() {
		if (instance == null) {
			instance =  new ClassPathXmlApplicationContext(minorConfig);
		}
		return instance;
	}
}
