package com.bazl.hslims.manager.model.core;

@SuppressWarnings("serial")
public class MatchCaculateResult implements java.io.Serializable
{
	public int isSuccessful=0;
	public SingleMarkerRate[] singleMarkers;
	public int matchCount=0;
	public double totalMatchPossibility = 0.0;
	public MatchCaculateResult(){}
	
	public MatchCaculateResult(SingleMarkerRate[] singleMarkers) 
	{
		//super();
		this.singleMarkers = singleMarkers;
	}
}
