package com.bazl.hslims.manager.model.submitMatchDefaultSetting;


import com.bazl.hslims.manager.model.core.MatchCaculateResult;
import com.bazl.hslims.manager.model.core.SingleMarkerRate;

public class WS_CaseParentageMatchResult {
	public int isSuccessful = 0;
	public int matchCount = 0;
	public double totalPossibility = 0.0;
	public WS_SingleMarkerRate[] singleMarkers ;
	
	public WS_CaseParentageMatchResult(WS_SingleMarkerRate[] singleMarkers)
	{
		this.singleMarkers = singleMarkers;
	}

	public WS_CaseParentageMatchResult(){}
	
	public int getMatchCount() {
		return matchCount;
	}
	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}
	public double getTotalPossibility() {
		return totalPossibility;
	}
	public void setTotalPossibility(double totalPossibility) {
		this.totalPossibility = totalPossibility;
	}

	public WS_SingleMarkerRate[] getSingleMarkers() {
		return singleMarkers;
	}
	public void setSingleMarkers(WS_SingleMarkerRate[] singleMarkers) {
		this.singleMarkers = singleMarkers;
	}
	
	public MatchCaculateResult convert2BloodMatchResult()
	{
		MatchCaculateResult bloodMatchResult = new MatchCaculateResult();
		bloodMatchResult.singleMarkers = new SingleMarkerRate[singleMarkers.length];
		for (int i = 0; i < singleMarkers.length; i++) {
			bloodMatchResult.singleMarkers[i] = singleMarkers[i].convert2SingleMarkerRate();
		}
		return  bloodMatchResult;
	}
	
	public void convertFromBloodMatchResult(MatchCaculateResult bloodMatchResult)
	{
		if(null != bloodMatchResult)
		{
			if(null != bloodMatchResult.singleMarkers && bloodMatchResult.singleMarkers.length > 0)
			{
				this.singleMarkers = new WS_SingleMarkerRate[bloodMatchResult.singleMarkers.length];
				for (int i = 0; i < bloodMatchResult.singleMarkers.length; i++) {
					this.singleMarkers[i] = new WS_SingleMarkerRate();
					this.singleMarkers[i].convertFromSingleMarkerRate(bloodMatchResult.singleMarkers[i]);
				}
			}
			this.matchCount = bloodMatchResult.matchCount;
			this.isSuccessful = bloodMatchResult.isSuccessful;
			this.totalPossibility = bloodMatchResult.totalMatchPossibility;
		}
	}

	public int getIsSuccessful() {
		return isSuccessful;
	}

	public void setIsSuccessful(int isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	
}
