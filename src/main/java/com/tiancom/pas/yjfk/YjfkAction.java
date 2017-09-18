package com.tiancom.pas.yjfk;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tiancom.pas.common.framework.context.LoginUser;
import com.tiancom.pas.common.framework.spring.SpringAppContext;
import com.tiancom.pas.common.framework.struts.PasBaseAction;

/**
 * 我
 * 
 * @author tancr
 * 
 */
@SuppressWarnings("unchecked")
public class YjfkAction extends PasBaseAction {

	public ActionForward saveYjfk(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ygbh = request.getSession().getAttribute("ygbh").toString();
		String fknr = request.getParameter("fknr");

		
		YjfkService service = (YjfkService) SpringAppContext.getBean(YjfkService.KEY, request);

		Map map = new HashMap();
		map.put("ygbh", ygbh);
		map.put("fknr", fknr);
		map.put("fksj", new Date());
		service.insertYhfk(map);

		
		return null;
	}

	@Override
	protected Class setClass() {
		// TODO Auto-generated method stub
		return this.getClass();
	}

}
