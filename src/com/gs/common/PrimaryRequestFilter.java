package com.gs.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrimaryRequestFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	*
	* The Filter will be onvoked only if the request url ends with ".go".
	* This is specified in web.xml.
	* The main task of this filter is get the requesting action name from the URL.
	* To do this is will remove the the ".go" string from the resource name that is passed.
	* It will then create a new request paramter and pass it to the ControlServlet along with the rest of the
	* request parameters.
	*/
	@Override
	public void doFilter(ServletRequest servReq, ServletResponse servResp,
			FilterChain filterChain) throws IOException, ServletException 
	{
		System.out.println("In Filter = ");
		HttpServletResponse httpResponse = (HttpServletResponse) servResp;
		HttpServletRequest httpRequest = (HttpServletRequest) servReq;
		System.out.println("Filter URI = " + httpRequest.getRequestURI() + " URL = " + httpRequest.getRequestURL());
		
		String sRequestUrl =  httpRequest.getRequestURL().toString();
		String sActionItem = sRequestUrl.substring(sRequestUrl.lastIndexOf("/") + 1);
		sActionItem = sActionItem.substring(0,sActionItem.lastIndexOf(".go"));
		
		servReq.setAttribute("request_action", sActionItem);
		
		ControlServlet.processRequest(httpRequest, httpResponse,servletContext);
	}

	ServletContext servletContext = null;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		servletContext = filterConfig.getServletContext();

	}

}
