package com.bazl.hslims.manager.model.basedata.impl;

import com.bazl.hslims.manager.model.basedata.PopulationAlleleFrequencyProvider;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/7.
 */
public class CachedPopulationAlleleFrequencyProvider implements PopulationAlleleFrequencyProvider {
    private Map<String, Double> cache;

    public CachedPopulationAlleleFrequencyProvider()
    {
        this.cache = new Hashtable();
    }

    public double get(String populationId, String markerName, String alleleName, double defaultFrequency)
    {
        Double result = (Double)this.cache.get(makeKey(populationId, markerName, alleleName));
        if (result == null) {
            return defaultFrequency;
        }
        return result.doubleValue();
    }

    public void clear() {
        this.cache.clear();
    }

    public void set(String populationId, String markerName, String alleleName, double frequency)
    {
        this.cache.put(makeKey(populationId, markerName, alleleName), Double.valueOf(frequency));
    }

    private String makeKey(String populationId, String markerName, String alleleName) {
        return populationId.toUpperCase() + "|" + markerName.toUpperCase() + "|" + alleleName;
    }
}
