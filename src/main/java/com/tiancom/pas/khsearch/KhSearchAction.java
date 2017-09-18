package com.tiancom.pas.khsearch;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tiancom.pas.common.framework.spring.SpringAppContext;
import com.tiancom.pas.common.framework.struts.PasBaseAction;


public class KhSearchAction extends PasBaseAction {

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String key = new String(request.getParameter("key").getBytes("ISO-8859-1"),"UTF-8");
		

		KhSearchService service = (KhSearchService) SpringAppContext.getBean(KhSearchService.KEY, request);
		List<Map> result = service.loadKh(key);
		
		
		return null;
	}

	@Override
	protected Class setClass() {
		return this.getClass();
	}

}
