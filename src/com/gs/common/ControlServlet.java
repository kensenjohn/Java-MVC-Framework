package com.gs.common;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.gs.bean.xml.ControlXmlBean;

/**
*
* The name Servlet is actually a misnommer. It is not exactly a Servlet. However it does work as a Controller in the MVC
* All request parameters are sent here from the PrimaryRequestFilter. The request parameter will also have an extra parameter 
* called "request_action" which is used by this Servlet to identify how the request has to be processed.
* 
* The request_action holds an identifier which will identify an action in the control.xml
* Ex: If a form submits with an action called "START_EVENT_DATE.go", then the PRimaryRequestFilter will create the "request_action"
* parameter with "START_EVENT_DATE". The Control Servlet will use that to identify the appropriate Action node from control.xml
*
* It will then get the attribute value "INIT_PROC" which will provide the Initial Processor and the method name. (This validates all request parameters).
*    If all the parameters are correct, then it will return a new object with an hashmap of request parameters which will be used
*    by the ServiceProcessor.
*    If there is an error then the error attribute "ERROR_MSG" is set.
*
* If successful, the object is passed to the ServiceProcessorObject which you can get from the "SERVICE_PROC" attribute of the Action.Here is where 
* the business logic is invoked.
*	IF all is successful, this will return an object which will be next used by the Dipatcher.
*
* The response from Service is then used to invoke the Dispatcher from the attribute "DISPATCHER". Depending on Success of failure, the child nodes of the 
* Action "RESULT" we can redirect to any JSP. The choice of JSP completely depends on the "STATUS". The Dispatcher checks to see which message has been set
* during the processing, then use that information and get the matching status from the control.xml and redirect it to that JSP.
* The Dispatcher will try and send only minimal information to requesting jsp. It is the responsibility of the JSP to load relevant data.
* 
*
*/
public class ControlServlet
{
	
	public static void processRequest( HttpServletRequest request, HttpServletResponse response, ServletContext servletContext ) throws  ServletException, IOException
	{
		String sRequestAction = request.getAttribute("request_action").toString();
		System.out.println("sRequestAction = " + sRequestAction);
		//sRequestAction = sRequestAction.substring(0,sRequestAction.lastIndexOf("."));
		
		// The entire control servlet will be stored in the ServletContext as a HashMap for faster processing.
		HashMap<String,ControlXmlBean> hmControlXmlBean  =  (HashMap<String,ControlXmlBean>)servletContext.getAttribute("CONTROL_XML");
		//System.out.println(hmControlXmlBean);
		try {
			invokeMethods( hmControlXmlBean.get(sRequestAction) , request, response,servletContext);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	*  Send all request parameters to the Initial Processor object specified in the INIT_PROC. This specifies the class name and the method name.
	*  InitProcessorObject is only used to store the result. This will hold info about whether all the data passed has been validated etc.
	*/
	private static InitProcessorObject invokeInitProcessor(  ControlXmlBean controlXmlBean ,  HttpServletRequest request, HttpServletResponse response, 
			ServletContext servletContext   )
			throws ClassNotFoundException, SecurityException, NoSuchMethodException, 
			IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, 
			IOException, ServletException, JSONException
	{
		
		
		InitProcessorObject initProcObject = new InitProcessorObject();
		
		String sInitClass = controlXmlBean.getInitProcClass();
		if(sInitClass !=null  && !"".equals(sInitClass))
		{
			String sInitMethod = controlXmlBean.getInitProcMethod();
			
			Class classIntitProc = Class.forName(sInitClass);
			
			 Class initParamType[] = new Class[1];
			 	initParamType[0] = HttpServletRequest.class;
			
			Method methodInitProc = classIntitProc.getMethod( sInitMethod , initParamType );
			
			Object initArglist[] = new Object[1];
			initArglist[0] = request;
			
			Constructor constInitProc = classIntitProc.getConstructor();
			
			Object arglist[] = new Object[0];
			Object initProcObj = constInitProc.newInstance(arglist);
			
			initProcObject = (InitProcessorObject) methodInitProc.invoke(initProcObj, initArglist);
			
			if(!initProcObject.isSuccess())
			{
				request.setAttribute("ERROR_MSG", initProcObject );
				
			}
			
		}
		return initProcObject;
	}
	
	/*
	*  The Service Processor is obtained from the "SERVICE_PROC" attribute
	*/
	private static ServiceProcessorObject invokeServiceProcObject( InitProcessorObject initProcObject ,  ControlXmlBean controlXmlBean)
		throws ClassNotFoundException, SecurityException, NoSuchMethodException, 
		IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, 
		IOException, ServletException, JSONException
	{
		
		ServiceProcessorObject serviceProcObject = new ServiceProcessorObject();
		
		String sServiceClass = controlXmlBean.getServiceProcClass();
		if(sServiceClass !=null  && !"".equals(sServiceClass))
		{
			String sServiceMethod = controlXmlBean.getServiceProcMethod();
			
			Class classServiceProc = Class.forName( sServiceClass );
			
			 Class serviceParamType[] = new Class[1];
			 serviceParamType[0] = InitProcessorObject.class;
			
			Method methodServiceProc = classServiceProc.getMethod( sServiceMethod , serviceParamType );
			
			Object serviceArglist[] = new Object[1];
			serviceArglist[0] = initProcObject;
			
			Constructor constServiceProc = classServiceProc.getConstructor();
			
			Object arglist[] = new Object[0];
			Object serviceProcObj = constServiceProc.newInstance(arglist);
			
			serviceProcObject = (ServiceProcessorObject) methodServiceProc.invoke(serviceProcObj, serviceArglist);
			
		}
		return serviceProcObject;
	}
	
	/*
	*  The "DISPATCHER" attribute specifies the dispatcher.
	*/
	private static void invokeDispatcher( InitProcessorObject initProcObject, ServiceProcessorObject serviceProcObject,  ControlXmlBean controlXmlBean ,  HttpServletRequest request, 
			HttpServletResponse response, ServletContext servletContext  ) 
			throws ClassNotFoundException, SecurityException, NoSuchMethodException, 
			IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, 
			IOException, ServletException, JSONException
	{
		Dispatcher dispatcher = new Dispatcher();
		
		String sDispatchClass = controlXmlBean.getDispatcherClass();
		if(sDispatchClass !=null  && !"".equals(sDispatchClass))
		{
			String sDispatchMethod = controlXmlBean.getDispatcherMethod();
			
			Class classDispatchProc = Class.forName( sDispatchClass );
			
			 Class dispathParamType[] = new Class[6];
			 dispathParamType[0] = InitProcessorObject.class;
			 dispathParamType[1] = ServiceProcessorObject.class;
			 dispathParamType[2] = HttpServletRequest.class;
			 dispathParamType[3] = HttpServletResponse.class;
			 dispathParamType[4] = ControlXmlBean.class;
			 dispathParamType[5] = ServletContext.class;
			
			Method methodDispatchProc = classDispatchProc.getMethod( sDispatchMethod , dispathParamType );
			
			Object dispatchArglist[] = new Object[6];
			dispatchArglist[0] = initProcObject;
			dispatchArglist[1] = serviceProcObject;
			dispatchArglist[2] = request;
			dispatchArglist[3] = response;
			dispatchArglist[4] = controlXmlBean;
			dispatchArglist[5] = servletContext;
			
			Constructor constDispatchProc = classDispatchProc.getConstructor();
			
			Object arglist[] = new Object[0];
			Object dispathProcObj = constDispatchProc.newInstance(arglist);
			
			dispatcher = (Dispatcher) methodDispatchProc.invoke(dispathProcObj, dispatchArglist);
			
		}
	}
	
	private static void invokeMethods( ControlXmlBean controlXmlBean ,  HttpServletRequest request, HttpServletResponse response, 
			ServletContext servletContext  ) throws ClassNotFoundException, SecurityException, NoSuchMethodException, 
			IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, 
			IOException, ServletException, JSONException
	{
		
		InitProcessorObject initProcObject = invokeInitProcessor(controlXmlBean, request, response, servletContext);
		
		ServiceProcessorObject serviceProcObject = invokeServiceProcObject(initProcObject,controlXmlBean);
		
		invokeDispatcher(  initProcObject, serviceProcObject, controlXmlBean , request, 
				response, servletContext );
		
		System.out.println("invokeMethods ServicePRoce = " + serviceProcObject.isSuccess() );
		
	}

}
