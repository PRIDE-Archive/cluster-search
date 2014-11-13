package uk.ac.ebi.pride.cluster.search.util;

import uk.ac.ebi.pride.cluster.search.model.ClusterQuality;

/**
 * @author ntoro
 * @since 13/11/14 16:56
 */
public class QualityAssigner {

    public static ClusterQuality calculateQuality(long numSpectra, double maxRatio) {

        ClusterQuality quality = ClusterQuality.UNKNOWN;

        if (maxRatio >= 0.7){
            if (numSpectra > 10) {
                quality = ClusterQuality.HIGH;
            } else {
                quality = ClusterQuality.MEDIUM;
            }
        }
        else {
            quality = ClusterQuality.LOW;
        }

        return quality;
    }
}
