package com.gs.bean.xml;

import java.util.HashMap;

public class ControlXmlBean 
{
	/*
	 * 
	 * <ACTION ID="CONFIRM_LOGIN" INIT_PROC="com.gs.init.InitLogin.proc" SERVICE_PROC="com.gs.serv.ServLogin.confirm" DISPATCHER="com.gs.disp.DispLogin.respond" >
	<RESULT STATUS="ERROR" TYPE="JSON" DESTINATION=""/>
	<RESULT STATUS="SUCCESS" TYPE="FORM" DESTINATION="web/com/gs/baselanding.jsp"/>
</ACTION>
	 */
	private String actionId = "";
	private String type = "";	
	private String initProcClass = "";
	private String initProcMethod = "";
	private String serviceProcClass = "";
	private String serviceProcMethod = "";
	private String dispatcherClass = "";
	private String dispatcherMethod = "";
	
	HashMap<String,ControlXmlBean.Result> hmResult = new HashMap<String, ControlXmlBean.Result>();
	
	public class Result
	{
		private String status = "";
		private String type = "";
		private String destination = "";
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getDestination() {
			return destination;
		}
		public void setDestination(String destination) {
			this.destination = destination;
		}
		@Override
		public String toString() {
			return "Result [status=" + status + ", type=" + type
					+ ", destination=" + destination + "]";
		}
		
		
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getInitProcClass() {
		return initProcClass;
	}

	public void setInitProcClass(String initProcClass) {
		this.initProcClass = initProcClass;
	}

	public String getInitProcMethod() {
		return initProcMethod;
	}

	public void setInitProcMethod(String initProcMethod) {
		this.initProcMethod = initProcMethod;
	}

	public String getServiceProcClass() {
		return serviceProcClass;
	}

	public void setServiceProcClass(String serviceProcClass) {
		this.serviceProcClass = serviceProcClass;
	}

	public String getServiceProcMethod() {
		return serviceProcMethod;
	}

	public void setServiceProcMethod(String serviceProcMethod) {
		this.serviceProcMethod = serviceProcMethod;
	}

	public String getDispatcherClass() {
		return dispatcherClass;
	}

	public void setDispatcherClass(String dispatcherClass) {
		this.dispatcherClass = dispatcherClass;
	}

	public String getDispatcherMethod() {
		return dispatcherMethod;
	}

	public void setDispatcherMethod(String dispatcherMethod) {
		this.dispatcherMethod = dispatcherMethod;
	}

	public HashMap<String, ControlXmlBean.Result> getHmResult() {
		return hmResult;
	}

	public void setHmResult(HashMap<String, ControlXmlBean.Result> hmResult) {
		this.hmResult = hmResult;
	}

	@Override
	public String toString() {
		return "ControlXmlBean [actionId=" + actionId + ", initProcClass="
				+ initProcClass + ", initProcMethod=" + initProcMethod
				+ ", serviceProcClass=" + serviceProcClass
				+ ", serviceProcMethod=" + serviceProcMethod
				+ ", dispatcherClass=" + dispatcherClass
				+ ", dispatcherMethod=" + dispatcherMethod + ", hmResult="
				+ hmResult + "]";
	}
	
}
