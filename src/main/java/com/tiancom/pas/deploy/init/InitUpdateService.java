package com.tiancom.pas.deploy.init;

public interface InitUpdateService {
	
	public static final String KEY = "InitUpdate.InitUpdateService";
	
	public void rebootProcessBxd() ;
	
	public void deleteBxd(String bxdh,String procInstId);
	
	public void deleteProcInstAndRebootBxdProc();
}
