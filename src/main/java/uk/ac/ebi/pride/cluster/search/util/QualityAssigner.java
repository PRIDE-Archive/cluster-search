package uk.ac.ebi.pride.cluster.search.util;

import uk.ac.ebi.pride.cluster.search.model.ClusterQuality;

/**
 * @author ntoro
 * @since 13/11/14 16:56
 */
public class QualityAssigner {

    public static String calculateQuality(long numSpectra, double maxRatio) {

        String quality = ClusterQuality.UNKNOWN.toString();

        if (maxRatio >= 0.7){
            if (numSpectra >= 10) {
                quality = ClusterQuality.HIGH.toString();
            } else {
                quality = ClusterQuality.MEDIUM.toString();
            }
        }
        else {
            quality = ClusterQuality.LOW.toString();
        }

        return quality;
    }
}
