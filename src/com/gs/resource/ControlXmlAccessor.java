package com.gs.resource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.gs.bean.xml.ControlXmlBean;

/**
* This class was used by the StartupServlet to create the control.xml hashmap. This uses jdom to parse the xml.
*
*/
public class ControlXmlAccessor {
	
	private String sFilePath = "";
	
	public ControlXmlAccessor(String sFilePath)
	{
		this.sFilePath = sFilePath;
	}
	
	public HashMap<String,ControlXmlBean> getControlXmlBean()
	{
		
		HashMap<String,ControlXmlBean> hmControlXmlBean  = new HashMap<String, ControlXmlBean>();
		SAXBuilder builder = new SAXBuilder();
    	File xmlFile = new File(this.sFilePath);
 
        try{
 
    	   Document document = (Document) builder.build(xmlFile);
           Element rootNode = document.getRootElement();
           //System.out.println("ActionList size ="+rootNode.getd);
           List actionList = rootNode.getChildren("ACTION");
           System.out.println("ActionList size ="+actionList.size());
           for (int ii=0; ii< actionList.size(); ii++)
           {
        	  // System.out.println("index ="+ii);
             Element node = (Element) actionList.get(ii);
             
             ControlXmlBean controlXmlBean = createControlXmlBean( node );
             
             hmControlXmlBean.put(controlXmlBean.getActionId(), controlXmlBean);
           }
 
    	 }catch(IOException io){
    		System.out.println(io.getMessage());
    	 }catch(JDOMException jdomex){
    		System.out.println(jdomex.getMessage());
    	}
		return hmControlXmlBean;
	}
	
	public  ControlXmlBean getControlXmlBean(String sControlName)
	{
		return null;
		
	}
	
	private ControlXmlBean createControlXmlBean( Element actionNode )
	{
		
		 String sActionId =  actionNode.getAttributeValue("ID");
		 String sInitProc =   actionNode.getAttributeValue("INIT_PROC");
		 String sServiceProc =   actionNode.getAttributeValue("SERVICE_PROC");
		 String sDispatcherProc =   actionNode.getAttributeValue("DISPATCHER");
		 String sType =  actionNode.getAttributeValue("TYPE");
		 
		 System.out.println("Init Proc = " + sInitProc);
		 
		 ControlXmlBean controlXmlBean = new ControlXmlBean();
		 controlXmlBean.setActionId(sActionId);
		 controlXmlBean.setType(sType);
		 controlXmlBean.setInitProcClass(getClassName(sInitProc));
		 controlXmlBean.setInitProcMethod(getMethodName(sInitProc));

		 controlXmlBean.setServiceProcClass(getClassName(sServiceProc));
		 controlXmlBean.setServiceProcMethod(getMethodName(sServiceProc));
		 
		 controlXmlBean.setDispatcherClass(getClassName(sDispatcherProc));
		 controlXmlBean.setDispatcherMethod(getMethodName(sDispatcherProc));
		 
		 List resultList = actionNode.getChildren();
		 
		 HashMap<String,ControlXmlBean.Result> hmControlResult = new HashMap<String, ControlXmlBean.Result>();
         
         for (int jj=0; jj< resultList.size(); jj++)
         {
        	 ControlXmlBean.Result controlResultBean = new ControlXmlBean().new Result();
        	 Element resultNode = (Element) resultList.get(jj);
        	 
        	 String sStatus = resultNode.getAttributeValue("STATUS");
        	 String sDestination = resultNode.getAttributeValue("DESTINATION");
        	 
        	 controlResultBean.setStatus(sStatus);
        	 controlResultBean.setDestination(sDestination);
        	 
        	 hmControlResult.put(sStatus, controlResultBean);
        	 
        	 System.out.println("STATUS : "  + resultNode.getAttributeValue("STATUS"));
             System.out.println("TYPE : "  + resultNode.getAttributeValue("TYPE"));
             System.out.println("DESTINATION : "  + resultNode.getAttributeValue("DESTINATION"));
         }
		 
         controlXmlBean.setHmResult(hmControlResult);
		 
		 return controlXmlBean;
	}
	
	private String getClassName(String sProcName)
	{
		
		String sClassName = sProcName.substring(0, sProcName.lastIndexOf("."));
		/*if(sToken!=null && !"".equalsIgnoreCase(sToken))
		{
			sClassName = sProcName.substring(0, sProcName.lastIndexOf("."));
		}*/
		return sClassName;
	}
	
	private String getMethodName(String sProcName)
	{
		String sMethodName = sProcName.substring(sProcName.lastIndexOf(".")+1);
		return sMethodName;
	}

}
