package uk.ac.ebi.pride.cluster.search.util;

import org.apache.commons.math.stat.StatUtils;

/**
 * Created by jdianes on 16/12/2014.
 */
public class LowResUtils {

    public static double[] toLowResByBucketMean(double[] input, int n) {
        assert(n<=input.length): "Input must be bigger or equal to output";

        double[] res = new double[n];
        int bucketSize = ((int) ((double)input.length/(double)n)) + 1; // double to int implies rounding down
        int bucketIndex = 0;
        for (int inputIndex=0; inputIndex<input.length; inputIndex=inputIndex+bucketSize) {
            res[bucketIndex] = StatUtils.mean(input, inputIndex, Math.min(bucketSize, input.length - inputIndex));
            bucketIndex++;
        }
        return res;
    }

}
