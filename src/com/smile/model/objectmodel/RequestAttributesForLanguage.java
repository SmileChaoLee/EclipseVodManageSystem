package com.smile.model.objectmodel;

import java.io.StringWriter;

import org.json.simple.JSONObject;

public class RequestAttributesForLanguage {
	
	private String actFunction;
	private int orgLanguageId;
	private String queryCondition;
	private String byOrder;
	private int pageNo;
	private String accessMethod;
	
	// constructor
	public RequestAttributesForLanguage()
	{
		this.actFunction = "LIST";
		this.orgLanguageId = 0;
		this.queryCondition = "";
		this.byOrder = "language.lang_no";
		this.pageNo = 1;
		this.accessMethod = "";
	}
	
	// copy an object of RequestAttributesForSong2 to a new object
	public void copyAttributes(RequestAttributesForLanguage attributes)
	{
		if (attributes != null)
		{
			setActFunction(attributes.actFunction);
			setOrgLanguageId(attributes.orgLanguageId);
			setQueryCondition(attributes.queryCondition);
			setByOrder(attributes.byOrder);
			setPageNo(attributes.pageNo);
			setAccessMethod(attributes.accessMethod);
		}
	}
	
	public void setActFunction(String actFunction)
	{
		if ( (actFunction == null) || (actFunction == "") )
		{
			actFunction = "LIST";
		}
		this.actFunction = actFunction;
	}
	public String getActFunction()
	{
		return this.actFunction;
	}
	
	public void setOrgLanguageId(int orgLanguageId)
	{
		this.orgLanguageId = orgLanguageId;
	}
	public int getOrgLanguageId()
	{
		return this.orgLanguageId;
	}
	
	public void setQueryCondition(String queryCondition)
	{
		if (queryCondition == null)
		{
			queryCondition = "";
		}
		this.queryCondition = queryCondition;
	}
	public String getQueryCondition()
	{
		return this.queryCondition;
	}
	
	public void setByOrder(String byOrder)
	{
		if ( (byOrder == null) || (byOrder == "") )
		{
			byOrder = "song2.song_no";
		}
		this.byOrder = byOrder;
	}
	public String getByOrder()
	{
		return this.byOrder;
	}
	
	public void setPageNo(int pageNo)
	{
		if (pageNo < 1)
		{
			pageNo = 1;
		}
		this.pageNo = pageNo;
	}
	public int getPageNo()
	{
		return this.pageNo;
	}
	
	public void setAccessMethod(String accessMethod)
	{
		if (accessMethod == null)
		{
			accessMethod = "";
		}
		this.accessMethod = accessMethod;
	}
	public String getAccessMethod()
	{
		return this.accessMethod;
	}
	
	@SuppressWarnings("unchecked")
	public String jsonString() {
		
		String result = "{}";
		
		try {
			JSONObject json = new JSONObject();
			json.put("actFunction", this.actFunction);
			json.put("orgLanguageId",this.orgLanguageId);
			json.put("queryCondition", this.queryCondition);
			json.put("byOrder", this.byOrder);
			json.put("pageNo", this.pageNo);
			json.put("accessMethod", this.accessMethod);	
			
			/*
			 * this is another way to have JSON string
			StringWriter out = new StringWriter();
			json.writeJSONString(out);
			if (out != null ) {
				result = out.toString();
			}
			*/
			
			// using this is enough
			result = json.toJSONString();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println("RequestAttributesForLanguage ---> String for JSONbject = " + result);
		
		return result;

	}
}
