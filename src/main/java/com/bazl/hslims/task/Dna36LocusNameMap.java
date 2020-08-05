package com.bazl.hslims.task;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-09-05.
 */
public class Dna36LocusNameMap {

    private static HashMap<String, String> dna36LocusNameMap;

    public static HashMap<String, String> getDna36LocusNameMap() {
        if(dna36LocusNameMap == null) {
            dna36LocusNameMap = new HashMap<>();
            dna36LocusNameMap.put("AMELOGENIN", "AMELOGENIN");
            dna36LocusNameMap.put("AMEL", "AMELOGENIN");
            dna36LocusNameMap.put("D8S1179", "D8S1179");
            dna36LocusNameMap.put("D21S11", "D21S11");
            dna36LocusNameMap.put("D18S51", "D18S51");
            dna36LocusNameMap.put("VWA", "VWA");
            dna36LocusNameMap.put("D3S1358", "D3S1358");
            dna36LocusNameMap.put("FGA", "FGA");
            dna36LocusNameMap.put("TH01", "TH01");
            dna36LocusNameMap.put("THO1", "TH01");
            dna36LocusNameMap.put("D5S818", "D5S818");
            dna36LocusNameMap.put("D13S317", "D13S317");
            dna36LocusNameMap.put("D7S820", "D7S820");
            dna36LocusNameMap.put("CSF1PO", "CSF1PO");
            dna36LocusNameMap.put("D16S539", "D16S539");
            dna36LocusNameMap.put("TPOX", "TPOX");
            dna36LocusNameMap.put("D2S1338", "D2S1338");
            dna36LocusNameMap.put("D19S433", "D19S433");
            dna36LocusNameMap.put("PENTA_D", "PENTA_D");
            dna36LocusNameMap.put("PENTAD", "PENTA_D");
            dna36LocusNameMap.put("PENTA D", "PENTA_D");
            dna36LocusNameMap.put("PENTA_E", "PENTA_E");
            dna36LocusNameMap.put("PENTAE", "PENTA_E");
            dna36LocusNameMap.put("PENTA E", "PENTA_E");
            dna36LocusNameMap.put("D6S1043", "D6S1043");
            dna36LocusNameMap.put("F13A01", "F13A01");
            dna36LocusNameMap.put("FESFPS", "FESFPS");
            dna36LocusNameMap.put("D1S80", "D1S80");
            dna36LocusNameMap.put("D12S391", "D12S391");
        }

        return dna36LocusNameMap;
    }

}
