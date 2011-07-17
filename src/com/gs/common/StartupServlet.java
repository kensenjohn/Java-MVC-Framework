package com.gs.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.gs.bean.xml.ControlXmlBean;
import com.gs.resource.ControlXmlAccessor;

/**
*  This Servlet is loaded on startup. It retrieves the location of the control.xml and then reads the entire xml.
*  It will convert the control.xml into a hashmap and store it in the ServletContext.
*
**/
public class StartupServlet extends HttpServlet 
{
	public void init(ServletConfig servletconfig) throws ServletException
    {
		//System.out.println("System startup");
		String sFileLocation = servletconfig.getInitParameter("file_location");
		//System.out.println("sFileLocation = " + sFileLocation);
		String sControlXmlLoc = "";
		try 
		{
			Properties props = new Properties();
			props.load(new FileInputStream(sFileLocation));
			sControlXmlLoc = props.getProperty("control_xml_loc");
		}
		catch(IOException e)
		{
			System.out.println("Error M<essage = " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("sControlXmlLoc = " + sControlXmlLoc);
		ControlXmlAccessor controlXml = new ControlXmlAccessor(sControlXmlLoc);
		
		HashMap<String,ControlXmlBean> hmControlXmlBean  = controlXml.getControlXmlBean();
		//System.out.println(controlXml.getControlXmlBean());
		
		servletconfig.getServletContext().setAttribute("CONTROL_XML", hmControlXmlBean);
    }

}
