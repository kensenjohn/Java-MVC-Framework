package com.gs.service;

import com.gs.common.InitProcessorObject;
import com.gs.common.ServiceProcessorObject;

public class ServiceEventStart {

	public ServiceEventStart() {
	}
	
	public ServiceProcessorObject servBegin( InitProcessorObject initProcessorObject )
	{
		ServiceProcessorObject serviceProcObject = new ServiceProcessorObject();
		
		if(initProcessorObject.isSuccess())
		{
			serviceProcObject.setSuccess(true);

			serviceProcObject.setResultStatus("SUCCESS");
		}
		else
		{
			initProcessorObject.setResultStatus("ERROR");
			serviceProcObject.setSuccess(false);
			serviceProcObject.setResultStatus("ERROR");
		}
		
		System.out.println(" Was init PRocess Success = " + initProcessorObject.isSuccess() );
		
		System.out.println(" InitPTovObject = " + initProcessorObject.getHmRequestObj() );
		
		
		
		return serviceProcObject;
	}

}
