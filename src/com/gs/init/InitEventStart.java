package com.gs.init;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.gs.common.InitProcessorObject;

public class InitEventStart {
	
	public InitProcessorObject initBegin( HttpServletRequest request )
	{
		System.out.println( request.getRemoteHost() );
		
		HashMap<String,String> hmRequestObj = new HashMap<String, String>();
		HashMap<String,String> hmErrorObj = new HashMap<String, String>();
		
		String sEventStartDt = request.getParameter("event_date");
		String sFormName = request.getParameter("form_name_1");
		
		hmRequestObj.put("event_date", sEventStartDt);
		hmRequestObj.put("form_name_1", sFormName);
		
		InitProcessorObject initProcObject = new InitProcessorObject();
		
		initProcObject.setHmRequestObj(hmRequestObj);
		initProcObject.setHmErrorObj(hmRequestObj);
		
		boolean isError = false;
		if(sEventStartDt == null || "".equalsIgnoreCase(sEventStartDt))
		{
			hmErrorObj.put( "event_date" , "Please enter a valid date (mm/dd/yyy). " );
			isError = true;			
		}
		
		hmErrorObj.put(sFormName, ""); //no message at the form level.
		if(isError)
		{
			initProcObject.setSuccess(false);
		}
		else
		{
			initProcObject.setSuccess(true);
		}
		System.out.println("End Init event start");
		return initProcObject;
	}

}
