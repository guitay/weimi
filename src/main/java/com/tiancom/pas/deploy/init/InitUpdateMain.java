package com.tiancom.pas.deploy.init;

public class InitUpdateMain {

	public static void main(String[] args) {
		InitUpdateService service = (InitUpdateService)SpringAppContext.getInstance().getBean(InitUpdateService.KEY);
		service.deleteProcInstAndRebootBxdProc();
//		service.deleteBxd("63", "248836");
	}
}
