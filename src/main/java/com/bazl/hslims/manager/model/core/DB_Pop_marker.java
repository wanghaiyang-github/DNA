package com.bazl.hslims.manager.model.core;


public class DB_Pop_marker 
{
	public String populationID;
	public String markerName;
	public int checkNum;
	public String comments;
	public DB_Pop_marker(){}
	
	public DB_Pop_marker(String populationID, String markerName, int checkNum, String comments)
	{
		this.populationID = populationID;
		this.markerName = markerName;
		this.checkNum = checkNum;
		this.comments = comments;
	}
	
	public Pop_Marker convert2Pop_Marker()
	{
		return new Pop_Marker(markerName,checkNum,comments);
	}
}
