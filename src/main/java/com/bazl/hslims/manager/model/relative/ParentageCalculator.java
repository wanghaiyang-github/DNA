package com.bazl.hslims.manager.model.relative;

import com.bazl.hslims.manager.model.relative.types.Marker;
import com.bazl.hslims.manager.model.relative.types.ParentageMatchOptions;
import com.bazl.hslims.manager.model.relative.types.ParentageMatchResult;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/7.
 */
public abstract interface ParentageCalculator {
    public abstract ParentageMatchResult calculate(Map<String, Marker> paramMap1, Map<String, Marker> paramMap2, Map<String, Marker> paramMap3, ParentageMatchOptions paramParentageMatchOptions);
}
