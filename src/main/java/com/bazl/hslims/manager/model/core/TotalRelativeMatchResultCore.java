package com.bazl.hslims.manager.model.core;

public class TotalRelativeMatchResultCore
{
	public int matchMode = 0; // 0 表示都没匹配上，1 表示匹配成功，2 表示只匹配父亲，3 表示只匹配母亲，4 表示单亲都匹配，双亲综合不匹配
	public MatchCaculateResult result;
	public MatchCaculateResult fResult;
	public MatchCaculateResult mResult;
	public TotalRelativeMatchResultCore(){}
	public TotalRelativeMatchResultCore(int matchMode, MatchCaculateResult result,
			MatchCaculateResult result2, MatchCaculateResult result3) {
		super();
		this.matchMode = matchMode;
		this.result = result;
		fResult = result2;
		mResult = result3;
	}
}
