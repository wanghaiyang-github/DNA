package com.bazl.hslims.manager.model.relative.impl;

import com.bazl.hslims.manager.model.basedata.PopulationAlleleFrequencyProvider;
import com.bazl.hslims.manager.model.basedata.impl.CachedPopulationAlleleFrequencyProvider;
import com.bazl.hslims.manager.model.relative.ParentageCalculator;
import com.bazl.hslims.manager.model.relative.types.Marker;
import com.bazl.hslims.manager.model.relative.types.ParentageMatchOptions;
import com.bazl.hslims.manager.model.relative.types.ParentageMatchResult;
import com.bazl.hslims.manager.model.relative.types.ParentageMatchResultRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/7.
 */
public class EssenMollerParentageCalculator implements ParentageCalculator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private PopulationAlleleFrequencyProvider freqProvider;

    private static double DEFAULT_MUTATION_RATE = 0.002D;

    private static double PI_MISMATCH = -1.0D;

    private static double DEFAULT_FREQUENCY = 0.001D;

    public void setPopulationAlleleFrequencyProvider(PopulationAlleleFrequencyProvider freqProvider)
    {
        this.freqProvider = freqProvider;
    }

    public ParentageMatchResult calculate(Map<String, Marker> af, Map<String, Marker> m, Map<String, Marker> c, ParentageMatchOptions options)
    {
        log.debug("进入calculate方法。");

        ParentageMatchResult result = new ParentageMatchResult();

        ParentageMatchResultRecord afcmRecord = new ParentageMatchResultRecord();
        ParentageMatchResultRecord afRecord = new ParentageMatchResultRecord();
        ParentageMatchResultRecord mRecord = new ParentageMatchResultRecord();

        afcmRecord.setPiOfMarker(new Hashtable());
        afRecord.setPiOfMarker(new Hashtable());
        mRecord.setPiOfMarker(new Hashtable());

        log.debug("遍历C的marker，计算三联体、二联体的PI值。");
        Double afPi;
        for (String markerName : c.keySet())
        {
            boolean canMatchM = RelativeCalculationHelper.findMatchAlleleName((Marker)c.get(markerName), (Marker)m.get(markerName)) != null;

            if ((canMatchM) && (af.get(markerName) != null)) {
                Double afcmPi = calculateAfcmPi(options.getPopulationId(), markerName, (Marker)af.get(markerName),
                        (Marker)m.get(markerName), (Marker)c.get(markerName));
                afcmRecord.getPiOfMarker().put(markerName, afcmPi);
            }

            afPi = calculateAfPi(options.getPopulationId(), markerName, (Marker)af.get(markerName), (Marker)c.get(markerName));
            afRecord.getPiOfMarker().put(markerName, afPi);

            Double mPi = calculateMPi(options.getPopulationId(), markerName, (Marker)m.get(markerName), (Marker)c.get(markerName));
            mRecord.getPiOfMarker().put(markerName, mPi);
        }

        log.debug("根据计算出来的PI值，计算三联体、二联体的matchCount。");
        afcmRecord.setMatchCount(calculateMatchCount(afcmRecord));
        afRecord.setMatchCount(calculateMatchCount(afRecord));
        mRecord.setMatchCount(calculateMatchCount(mRecord));

        log.debug("根据计算出来的PI值，计算三联体、二联体的diffCount。");
        afcmRecord.setDiffCount(calculateDiffCount(afcmRecord));
        afRecord.setDiffCount(calculateDiffCount(afRecord));
        mRecord.setDiffCount(calculateDiffCount(mRecord));

        log.debug("检查三联体、二联体是否满足options中给出的条件。");
        afcmRecord.setSuccessful(isFitOptions(afcmRecord, options));
        afRecord.setSuccessful(isFitOptions(afRecord, options));
        mRecord.setSuccessful(isFitOptions(mRecord, options));

        log.debug("计算突变基因座的PI");
        if (afcmRecord.isSuccessful()) {
            for (String markerName : afcmRecord.getPiOfMarker().keySet()) {
                Double piOfMutation = (Double)afcmRecord.getPiOfMarker().get(markerName);
                if ((Double.isNaN(piOfMutation.doubleValue())) || (piOfMutation.doubleValue() < 0.0D)) {
                    log.debug("突变基因座：" + markerName);
                    afPi = 3.0;
                    piOfMutation = calculateAfcmPiOfMutation(options.getPopulationId(), markerName,
                            (Marker)af.get(markerName), (Marker)m.get(markerName), (Marker)c.get(markerName), 3);
                    afcmRecord.getPiOfMarker().put(markerName, piOfMutation);
                }
            }

        }

        log.debug("计算三联体、二联体的累计PI。");
        afcmRecord.setPi(calculateTotalPi(afcmRecord.getPiOfMarker().values()));
        afRecord.setPi(calculateTotalPi(afRecord.getPiOfMarker().values()));
        mRecord.setPi(calculateTotalPi(mRecord.getPiOfMarker().values()));

        result.setResult(afcmRecord);
        result.setResultOfAf(afRecord);
        result.setResultOfM(mRecord);

        log.debug("计算完毕，返回结果。");
        return result;
    }

    private Double calculateProbability(Marker parent, String childAllele, double childAlleleFreq, int maxStep, double mutationRate)
    {
        if (parent == null) {
            return Double.valueOf(childAlleleFreq);
        }

        double matchCount = 0.0D;
        double mutationFactor = 0.0D;
        for (String parentAllele : parent) {
            int mutationStep = (int)Math.abs(Double.parseDouble(parentAllele) - Double.parseDouble(childAllele));
            if (mutationStep == 0) {
                matchCount += 1.0D; } else {
                if (mutationStep > maxStep) {
                    continue;
                }
                mutationFactor += Math.pow(0.1D, mutationStep - 1);
            }
        }
        return Double.valueOf(matchCount != 0.0D ? matchCount / 2.0D : mutationRate * 0.25D * mutationFactor);
    }

    private Double calculateAfcmPiOfMutation(String populationId, String markerName, Marker af, Marker m, Marker c, int maxMutationStep)
    {
        checkMarker(af);
        checkMarker(m);
        checkMarker(c);

        String childAllele1 = (String)c.get(0);
        double p1 = this.freqProvider.get(populationId, markerName, childAllele1, DEFAULT_FREQUENCY);

        String childAllele2 = (String)c.get(1);
        double p2 = this.freqProvider.get(populationId, markerName, childAllele2, DEFAULT_FREQUENCY);

        Double m1 = calculateProbability(m, childAllele1, p1, maxMutationStep, DEFAULT_MUTATION_RATE / 3.5D);

        Double m2 = calculateProbability(m, childAllele2, p2, maxMutationStep, DEFAULT_MUTATION_RATE / 3.5D);

        Double af1 = Double.valueOf(0.0D);
        if ((!m.contains(childAllele1)) || (m.contains(childAllele2))) {
            af1 = calculateProbability(af, childAllele1, p1, maxMutationStep, DEFAULT_MUTATION_RATE);
        }
        Double af2 = Double.valueOf(0.0D);
        if ((!m.contains(childAllele2)) || (m.contains(childAllele1))) {
            af2 = calculateProbability(af, childAllele2, p2, maxMutationStep, DEFAULT_MUTATION_RATE);
        }
        Double rm1 = calculateProbability(null, childAllele1, p1, maxMutationStep, DEFAULT_MUTATION_RATE);

        Double rm2 = calculateProbability(null, childAllele2, p2, maxMutationStep, DEFAULT_MUTATION_RATE);

        Double X = Double.valueOf(m1.doubleValue() * af2.doubleValue() + m2.doubleValue() * af1.doubleValue());
        Double Y = Double.valueOf(m1.doubleValue() * rm2.doubleValue() + m2.doubleValue() * rm1.doubleValue());
        return Double.valueOf(X.doubleValue() / Y.doubleValue());
    }

    private double calculateTotalPi(Collection<Double> piOfMarker) {
        double result = 1.0D;

        for (Double pi : piOfMarker) {
            if ((!Double.isNaN(pi.doubleValue())) && (pi.doubleValue() > 0.0D)) {
                result *= pi.doubleValue();
            }
        }
        return result;
    }

    private boolean isFitOptions(ParentageMatchResultRecord record, ParentageMatchOptions options)
    {
        return (record.getMatchCount() >= options.getMatchThreshold()) &&
                (record.getDiffCount() <= options.getHalfDiffCount());
    }

    private int calculateMatchCount(ParentageMatchResultRecord record) {
        int result = 0;

        for (Double pi : record.getPiOfMarker().values()) {
            if ((!Double.isNaN(pi.doubleValue())) && (pi.doubleValue() > 0.0D)) {
                result++;
            }
        }
        return result;
    }

    private int calculateDiffCount(ParentageMatchResultRecord record) {
        int result = 0;

        for (Double pi : record.getPiOfMarker().values()) {
            if (pi.doubleValue() < 0.0D) {
                result++;
            }
        }
        return result;
    }

    private Double calculateMPi(String populationId, String markerName, Marker m, Marker c) {
        return calculateAfPi(populationId, markerName, m, c);
    }

    private Double calculateAfPi(String populationId, String markerName, Marker af, Marker c)
    {
        checkMarker(af);
        checkMarker(c);

        if ((af == null) || (c == null)) {
            return Double.valueOf((0.0D / 0.0D));
        }
        boolean afIsHomozygote = RelativeCalculationHelper.isHomozygote(af);
        boolean cIsHomozygote = RelativeCalculationHelper.isHomozygote(c);

        if ((!afIsHomozygote) && (!cIsHomozygote) && (RelativeCalculationHelper.isOnlyOneMatch(c, af)))
        {
            String qAlleleName = RelativeCalculationHelper.findMatchAlleleName(af, c);
            double q = this.freqProvider.get(populationId, markerName, qAlleleName, DEFAULT_FREQUENCY);

            return Double.valueOf(1.0D / (4.0D * q));
        }

        if ((!afIsHomozygote) && (!cIsHomozygote) && (RelativeCalculationHelper.allAlleleNamesAreSame(c, af)))
        {
            String pAlleleName = (String)c.get(0);
            String qAlleleName = (String)c.get(1);
            double p = this.freqProvider.get(populationId, markerName, pAlleleName, DEFAULT_FREQUENCY);
            double q = this.freqProvider.get(populationId, markerName, qAlleleName, DEFAULT_FREQUENCY);

            return Double.valueOf(1.0D / (4.0D * p) + 1.0D / (4.0D * q));
        }

        if (((!afIsHomozygote) && (cIsHomozygote)) || ((afIsHomozygote) && (!cIsHomozygote) &&
                (RelativeCalculationHelper.findMatchAlleleName(af, c) != null)))
        {
            String qAlleleName = RelativeCalculationHelper.findMatchAlleleName(af, c);
            double q = this.freqProvider.get(populationId, markerName, qAlleleName, DEFAULT_FREQUENCY);

            return Double.valueOf(1.0D / (2.0D * q));
        }

        if ((afIsHomozygote) && (cIsHomozygote) && (RelativeCalculationHelper.allAlleleNamesAreSame(af, c)))
        {
            String qAlleleName = (String)c.get(0);
            double q = this.freqProvider.get(populationId, markerName, qAlleleName, DEFAULT_FREQUENCY);

            return Double.valueOf(1.0D / q);
        }

        return Double.valueOf(PI_MISMATCH);
    }

    private Double calculateAfcmPi(String populationId, String markerName, Marker af, Marker m, Marker c)
    {
        checkMarker(af);
        checkMarker(m);
        checkMarker(c);

        if ((af == null) || (c == null) || (m == null)) {
            return Double.valueOf((0.0D / 0.0D));
        }
        boolean afIsHomozygote = RelativeCalculationHelper.isHomozygote(af);
        boolean cIsHomozygote = RelativeCalculationHelper.isHomozygote(c);
        boolean mIsHomozygote = RelativeCalculationHelper.isHomozygote(m);

        if ((!afIsHomozygote) &&
                (!cIsHomozygote) &&
                (!mIsHomozygote) &&
                (RelativeCalculationHelper.isOnlyOneMatch(c, m)) &&
                (RelativeCalculationHelper.isOnlyOneMatch(c, af)) &&
                (!RelativeCalculationHelper.findMatchAlleleName(c, m).equalsIgnoreCase(
                        RelativeCalculationHelper.findMatchAlleleName(c, af))))
        {
            String qAlleleName = RelativeCalculationHelper.findMatchAlleleName(af, c);
            double q = this.freqProvider.get(populationId, markerName, qAlleleName, DEFAULT_FREQUENCY);

            return Double.valueOf(1.0D / (2.0D * q));
        }

        if ((!afIsHomozygote) &&
                (!cIsHomozygote) &&
                (mIsHomozygote) &&
                (RelativeCalculationHelper.isOnlyOneMatch(c, m)) &&
                (RelativeCalculationHelper.isOnlyOneMatch(c, af)) &&
                (!RelativeCalculationHelper.findMatchAlleleName(c, m).equalsIgnoreCase(
                        RelativeCalculationHelper.findMatchAlleleName(c, af)))) {
            String qAlleleName = RelativeCalculationHelper.findMatchAlleleName(af, c);
            double q = this.freqProvider.get(populationId, markerName, qAlleleName, DEFAULT_FREQUENCY);

            return Double.valueOf(1.0D / (2.0D * q));
        }

        if ((!afIsHomozygote) && (!cIsHomozygote) && (!mIsHomozygote) && (RelativeCalculationHelper.isOnlyOneMatch(c, m)) &&
                (RelativeCalculationHelper.allAlleleNamesAreSame(c, af))) {
            String qAlleleName = RelativeCalculationHelper.findNotMatchAlleleName(c, m);
            double q = this.freqProvider.get(populationId, markerName, qAlleleName, DEFAULT_FREQUENCY);

            return Double.valueOf(1.0D / (2.0D * q));
        }

        if ((!afIsHomozygote) && (!cIsHomozygote) && (mIsHomozygote) && (RelativeCalculationHelper.isOnlyOneMatch(c, m)) &&
                (RelativeCalculationHelper.allAlleleNamesAreSame(c, af))) {
            String qAlleleName = RelativeCalculationHelper.findNotMatchAlleleName(c, m);
            double q = this.freqProvider.get(populationId, markerName, qAlleleName, DEFAULT_FREQUENCY);

            return Double.valueOf(1.0D / (2.0D * q));
        }

        if ((!afIsHomozygote) && (cIsHomozygote) && (!mIsHomozygote) && (RelativeCalculationHelper.isOnlyOneMatch(af, c)) &&
                (RelativeCalculationHelper.isOnlyOneMatch(m, c)) &&
                (!RelativeCalculationHelper.allAlleleNamesAreSame(af, m))) {
            String qAlleleName = (String)c.get(0);
            double q = this.freqProvider.get(populationId, markerName, qAlleleName, DEFAULT_FREQUENCY);

            return Double.valueOf(1.0D / (2.0D * q));
        }

        if ((!afIsHomozygote) && (cIsHomozygote) && (!mIsHomozygote) && (RelativeCalculationHelper.isOnlyOneMatch(af, c)) &&
                (RelativeCalculationHelper.isOnlyOneMatch(m, c)) &&
                (RelativeCalculationHelper.allAlleleNamesAreSame(af, m))) {
            String qAlleleName = (String)c.get(0);
            double q = this.freqProvider.get(populationId, markerName, qAlleleName, DEFAULT_FREQUENCY);

            return Double.valueOf(1.0D / (2.0D * q));
        }

        if ((!afIsHomozygote) && (cIsHomozygote) && (mIsHomozygote) && (RelativeCalculationHelper.allAlleleNamesAreSame(c, m)) &&
                (RelativeCalculationHelper.isOnlyOneMatch(af, c))) {
            String qAlleleName = (String)c.get(0);
            double q = this.freqProvider.get(populationId, markerName, qAlleleName, DEFAULT_FREQUENCY);

            return Double.valueOf(1.0D / (2.0D * q));
        }

        if ((afIsHomozygote) && (c.contains(af.get(0))) && (RelativeCalculationHelper.findMatchAlleleName(c, m) != null) && (
                (!RelativeCalculationHelper.allAlleleNamesAreSame(c, m)) || ((cIsHomozygote) && (mIsHomozygote)))) {
            String qAlleleName = (String)af.get(0);
            double q = this.freqProvider.get(populationId, markerName, qAlleleName, DEFAULT_FREQUENCY);

            return Double.valueOf(1.0D / q);
        }

        if ((!afIsHomozygote) && (!cIsHomozygote) && (!mIsHomozygote) &&
                (RelativeCalculationHelper.allAlleleNamesAreSame(c, m)) &&
                (!RelativeCalculationHelper.allAlleleNamesAreSame(af, c)) &&
                (RelativeCalculationHelper.findMatchAlleleName(af, c) != null)) {
            double p = this.freqProvider.get(populationId, markerName, (String)c.get(0), DEFAULT_FREQUENCY);
            double q = this.freqProvider.get(populationId, markerName, (String)c.get(1), DEFAULT_FREQUENCY);

            return Double.valueOf(1.0D / (2.0D * p + 2.0D * q));
        }

        if ((!afIsHomozygote) && (!cIsHomozygote) && (!mIsHomozygote) &&
                (RelativeCalculationHelper.allAlleleNamesAreSame(c, m)) &&
                (RelativeCalculationHelper.allAlleleNamesAreSame(af, c))) {
            double p = this.freqProvider.get(populationId, markerName, (String)c.get(0), DEFAULT_FREQUENCY);
            double q = this.freqProvider.get(populationId, markerName, (String)c.get(1), DEFAULT_FREQUENCY);

            return Double.valueOf(1.0D / (p + q));
        }

        if ((afIsHomozygote) && (!cIsHomozygote) && (!mIsHomozygote) && (RelativeCalculationHelper.allAlleleNamesAreSame(c, m)) &&
                (c.contains(af.get(0)))) {
            double p = this.freqProvider.get(populationId, markerName, (String)c.get(0), DEFAULT_FREQUENCY);
            double q = this.freqProvider.get(populationId, markerName, (String)c.get(1), DEFAULT_FREQUENCY);

            return Double.valueOf(1.0D / (p + q));
        }

        return Double.valueOf(PI_MISMATCH);
    }

    private void checkMarker(Marker m)
    {
        if ((m != null) && (m.size() != 2)) {
            log.error("计算亲缘的Marker不为null，包含的allele个数不是2，异常结束！");
            throw new RuntimeException();
        }
    }
}
