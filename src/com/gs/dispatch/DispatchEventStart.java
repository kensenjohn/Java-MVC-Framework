package com.gs.dispatch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.gs.bean.xml.ControlXmlBean;
import com.gs.common.InitProcessorObject;
import com.gs.common.ServiceProcessorObject;

public class DispatchEventStart {

	public void dispBegin(InitProcessorObject initProcObject, 
			ServiceProcessorObject servProcObject, HttpServletRequest request, HttpServletResponse response,  
			ControlXmlBean controlXmlBean, ServletContext servletContext  )
	{
		try 
		{
			
			HashMap<String,ControlXmlBean.Result> hmResult = controlXmlBean.getHmResult();
			
			String sType = controlXmlBean.getType();
			

			Set<String> setResult = hmResult.keySet();
			
			
			
			if(initProcObject.getResultStatus()!=null && "ERROR".equalsIgnoreCase(initProcObject.getResultStatus()))
			{
				for( String sResultKey : setResult )
				{
					if( "ERROR".equalsIgnoreCase(sResultKey)  )
					{
						ControlXmlBean.Result resultDest = hmResult.get(sResultKey);
						response.sendRedirect(resultDest.getDestination());
					}
				}
			}
			else
			{
				for( String sResultKey : setResult )
				{
					if( "SUCCESS".equalsIgnoreCase(sResultKey)  )
					{
						ControlXmlBean.Result resultDest = hmResult.get(sResultKey);
						response.sendRedirect(resultDest.getDestination());
					}
				}
			}
			
			/*
			 String sType = controlXmlBean.getType();
			Set<String> setResult = hmResult.keySet();
			
			String sDestination = "";
			if( !initProcObject.isSuccess() )
			{
				for( String sResultKey : setResult )
				{
					ControlXmlBean.Result result = hmResult.get(sResultKey);
					
					
					
					if( "ERROR".equalsIgnoreCase(sResultKey))
					{
						sDestination = result.getDestination();
						
						break;
						
						
					}
					
					
				}
			}
			
			

			if( "FORM".equalsIgnoreCase(sType))
			{
				RequestDispatcher rd = servletContext.getRequestDispatcher(sDestination);
				rd.forward(request, response);
				response.sendRedirect("/web/com/gs/login.jsp");
			}
			else if( "JSON".equalsIgnoreCase(sType) )
			{
				JSONObject jsonObject = new JSONObject();
				
				jsonObject.put("ERROR_MSG", initProcObject.getJson());
				
				
				System.out.println("Before Write");
				response.getWriter().write(jsonObject.toString());
				System.out.println("Before Dispatch");
			}*/
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}
