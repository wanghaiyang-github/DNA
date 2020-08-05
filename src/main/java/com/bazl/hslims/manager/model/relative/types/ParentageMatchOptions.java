package com.bazl.hslims.manager.model.relative.types;

/**
 * Created by Administrator on 2017/9/7.
 */
public class ParentageMatchOptions {

    private int matchThreshold;

    private int halfDiffCount;

    private String populationId;

    public int getMatchThreshold()
    {
        return this.matchThreshold;
    }

    public void setMatchThreshold(int matchThreshold) {
        this.matchThreshold = matchThreshold;
    }

    public int getHalfDiffCount() {
        return this.halfDiffCount;
    }

    public void setHalfDiffCount(int halfDiffCount) {
        this.halfDiffCount = halfDiffCount;
    }

    public String getPopulationId() {
        return this.populationId;
    }

    public void setPopulationId(String populationId) {
        this.populationId = populationId;
    }

}
