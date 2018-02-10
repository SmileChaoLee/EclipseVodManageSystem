package com.smile.model.objectmodel;

public class RequestAttributesForSong {
	
	private String actFunction;
	private String orgSong_no;
	private String queryCondition;
	private String byOrder;
	private int pageNo;
	private String accessMethod;
	
	// constructor
	public RequestAttributesForSong()
	{
		this.actFunction = "LIST";
		this.orgSong_no = "";
		this.queryCondition = "";
		this.byOrder = "song2.song_no";
		this.pageNo = 1;
		this.accessMethod = "";
	}
	
	// copy an object of RequestAttributesForSong2 to a new object
	public void copyAttributes(RequestAttributesForSong attributes)
	{
		if (attributes != null) {
			setActFunction(attributes.actFunction);
			setOrgSong_no(attributes.orgSong_no);
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
	
	public void setOrgSong_no(String orgSong_no)
	{
		if (orgSong_no == null)
		{
			orgSong_no = "";
		}
		this.orgSong_no = orgSong_no;
	}
	public String getOrgSong_no()
	{
		return this.orgSong_no;
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
}
