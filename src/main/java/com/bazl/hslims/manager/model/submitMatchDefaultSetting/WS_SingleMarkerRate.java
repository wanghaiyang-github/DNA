package com.bazl.hslims.manager.model.submitMatchDefaultSetting;


import com.bazl.hslims.manager.model.core.SingleMarkerRate;

public class WS_SingleMarkerRate {
	public String markerName;
	public double markerRate;
	public int matchMode;
	public WS_SingleMarkerRate(){}
	public WS_SingleMarkerRate(String markerName, double markerRate) {
		this.markerName = markerName;
		this.markerRate = markerRate;
	}
	public String getMarkerName() {
		return markerName;
	}
	public void setMarkerName(String markerName) {
		this.markerName = markerName;
	}
	public double getMarkerRate() {
		return markerRate;
	}
	public void setMarkerRate(double markerRate) {
		this.markerRate = markerRate;
	}
	
	public SingleMarkerRate convert2SingleMarkerRate()
	{
		return new SingleMarkerRate(markerName,markerRate);
	}
	
	public void convertFromSingleMarkerRate(SingleMarkerRate single)
	{
		if(null != single)
		{
			this.markerName = single.markerName;
			this.markerRate = single.markerRate;
			this.matchMode = single.matchMode;
		}
	}
	public int getMatchMode() {
		return matchMode;
	}
	public void setMatchMode(int matchMode) {
		this.matchMode = matchMode;
	}
}
