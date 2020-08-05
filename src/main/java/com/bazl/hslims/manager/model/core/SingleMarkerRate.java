package com.bazl.hslims.manager.model.core;

public class SingleMarkerRate implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String markerName;
	public double markerRate;
	public int matchMode = 0; //0 表示匹配上，1表示没匹配上
	
	public SingleMarkerRate(String markerName, double markerRate) 
	{
		this.markerName = markerName;
		this.markerRate = markerRate;
	}
	public SingleMarkerRate(){}
}
