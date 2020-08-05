package com.bazl.hslims.manager.model.submitMatchDefaultSetting;


import com.bazl.hslims.manager.model.core.TotalRelativeMatchResultCore;

public class WS_TotalRelativeMatchResult
{
	public int matchMode = 0; // 0 表示都没匹配上，1 表示匹配成功，2 表示只匹配父亲，3 表示只匹配母亲，4 表示单亲都匹配，双亲综合不匹配
	public WS_CaseParentageMatchResult result;
	public WS_CaseParentageMatchResult fResult;
	public WS_CaseParentageMatchResult mResult;
	public String fBarcode;
	public String mBarcode;
	public String cBarcode;
	
	public void convertFromTotalRelativeMatchResult(TotalRelativeMatchResultCore result)
	{
		this.matchMode = result.matchMode;
		this.result = new WS_CaseParentageMatchResult();
		this.fResult = new WS_CaseParentageMatchResult();
		this.mResult = new WS_CaseParentageMatchResult();
		this.result.convertFromBloodMatchResult(result.result);
		this.fResult.convertFromBloodMatchResult(result.fResult);
		this.mResult.convertFromBloodMatchResult(result.mResult);
	}
	public WS_TotalRelativeMatchResult(){}
	public int getMatchMode() {
		return matchMode;
	}
	public void setMatchMode(int matchMode) {
		this.matchMode = matchMode;
	}
	public WS_CaseParentageMatchResult getResult() {
		return result;
	}
	public void setResult(WS_CaseParentageMatchResult result) {
		this.result = result;
	}
	public WS_CaseParentageMatchResult getFResult() {
		return fResult;
	}
	public void setFResult(WS_CaseParentageMatchResult result) {
		fResult = result;
	}
	public WS_CaseParentageMatchResult getMResult() {
		return mResult;
	}
	public void setMResult(WS_CaseParentageMatchResult result) {
		mResult = result;
	}
	public WS_TotalRelativeMatchResult(int matchMode,
			WS_CaseParentageMatchResult result,
			WS_CaseParentageMatchResult result2,
			WS_CaseParentageMatchResult result3) {
		super();
		this.matchMode = matchMode;
		this.result = result;
		fResult = result2;
		mResult = result3;
	}
	public String getCBarcode() {
		return cBarcode;
	}
	public void setCBarcode(String barcode) {
		cBarcode = barcode;
	}
	public String getFBarcode() {
		return fBarcode;
	}
	public void setFBarcode(String barcode) {
		fBarcode = barcode;
	}
	public String getMBarcode() {
		return mBarcode;
	}
	public void setMBarcode(String barcode) {
		mBarcode = barcode;
	}
}
