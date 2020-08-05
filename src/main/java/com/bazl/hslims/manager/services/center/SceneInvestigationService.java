package com.bazl.hslims.manager.services.center;

import com.bazl.hslims.manager.model.XkFeedbackCaseInfo;
import com.bazl.hslims.manager.model.po.LimsCaseInfo;

import java.util.List;
import java.util.Map;

public abstract interface SceneInvestigationService
{
    /*public abstract Map<String, Object> findSceneInvestigationByNo(String paramString)
            throws Exception;*/
    public abstract Map<String, Object> findSceneInvestigationByNo(String paramString)
            throws Exception;

    public abstract void selectAllNotFeedbak();

    public void updateStatus();
}
