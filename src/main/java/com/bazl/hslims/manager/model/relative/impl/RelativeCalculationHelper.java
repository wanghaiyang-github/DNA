package com.bazl.hslims.manager.model.relative.impl;

import com.bazl.hslims.manager.model.relative.types.Marker;

/**
 * Created by Administrator on 2017/9/7.
 */
public class RelativeCalculationHelper {

    public static boolean isOnlyOneMatch(Marker m1, Marker m2)
    {
        return ((m2.contains(m1.get(0))) && (!m2.contains(m1.get(1)))) || (
                (m2.contains(m1.get(1))) && (!m2.contains(m1.get(0))));
    }

    public static boolean isHomozygote(Marker m) {
        if ((m == null) || (m.size() == 0)) {
            return false;
        }
        String firstAlleleName = (String)m.get(0);

        for (String alleleName : m) {
            if (!alleleName.equalsIgnoreCase(firstAlleleName)) {
                return false;
            }
        }
        return true;
    }

    public static String findMatchAlleleName(Marker m1, Marker m2)
    {
        if ((m1 == null) || (m2 == null)) {
            return null;
        }
        for (String alleleName : m1) {
            if (m2.contains(alleleName)) {
                return alleleName;
            }
        }
        return null;
    }

    public static String findNotMatchAlleleName(Marker m1, Marker m2)
    {
        for (String alleleName : m1) {
            if (!m2.contains(alleleName)) {
                return alleleName;
            }
        }
        return null;
    }

    public static boolean allAlleleNamesAreSame(Marker m1, Marker m2) {
        return (m1.containsAll(m2)) && (m2.containsAll(m1));
    }

}
