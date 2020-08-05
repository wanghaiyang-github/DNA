package com.bazl.hslims.manager.model.basedata;

/**
 * Created by Administrator on 2017/9/7.
 */
public abstract interface PopulationAlleleFrequencyProvider {
    public abstract double get(String paramString1, String paramString2, String paramString3, double paramDouble);
}
