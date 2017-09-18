package com.tiancom.pas.qa;

import com.tiancom.pas.common.result.ResultBean;
import com.tiancom.pas.common.result.ResultCommon;
import com.tiancom.pas.common.result.ResultListData;

public interface QAService {

	final static String KEY = "QA.QAService";

	public ResultCommon saveQA(QA qa);
	
	public ResultBean<QA> queryByKey(Integer wtid);

	public ResultListData<QA> queryByCondition(Integer flid, String bzwt);

	public ResultCommon deleteQAByWtid(Integer wtid);
	
	public void rebuildAllIndex() ;


}
