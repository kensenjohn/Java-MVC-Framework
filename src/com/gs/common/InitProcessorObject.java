package com.gs.common;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class InitProcessorObject 
{
	
	private HashMap<String,String> hmRequestObj = new HashMap<String, String>();
	private HashMap<String,String> hmErrorObj = new HashMap<String, String>();
	
	private boolean isSuccess = true;
	private String resultStatus = "";

	public HashMap<String, String> getHmRequestObj() {
		return hmRequestObj;
	}

	public void setHmRequestObj(HashMap<String, String> hmRequestObj) {
		this.hmRequestObj = hmRequestObj;
	}

	public HashMap<String, String> getHmErrorObj() {
		return hmErrorObj;
	}

	public void setHmErrorObj(HashMap<String, String> hmErrorObj) {
		this.hmErrorObj = hmErrorObj;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	
	
	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public JSONObject getJson() throws JSONException
	{
		JSONObject jsIsSuccess = new  JSONObject();
		jsIsSuccess.put("is_success", isSuccess);
		
		JSONObject jsInitProcObj = new JSONObject();
		jsInitProcObj.put("init_proc_obj", jsIsSuccess);
		
		return jsInitProcObj;
	}
}
