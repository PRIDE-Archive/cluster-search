package uk.ac.ebi.pride.cluster.search.model;

import org.apache.solr.client.solrj.beans.Field;

import java.util.List;
import java.util.Map;

import static uk.ac.ebi.pride.cluster.search.model.ClusterFields.*;

/**
 * @author ntoro
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 *
 */
public class Cluster {

    @Field(ID)
    long clusterId;

    @Field(HIGHEST_RATIO_PEP_SEQUENCE)
    List<String> highestRatioPepSequences;

    @Field(HIGHEST_RATIO_PROTEIN_ACCESSION)
    List<String> highestRatioProteinAccessions;

    @Field(NUMBER_OF_SPECTRA)
    long numberOfSpectra;

    @Field(MAX_RATIO)
    double maxRatio;

    @Field(CLUSTER_QUALITY)
    ClusterQuality clusterQuality;

    @Field(PROJECT_ASSAYS)
    Map<String, List<String>> projectAssays;

    public long getClusterId() {
        return clusterId;
    }

    public void setClusterId(long clusterId) {
        this.clusterId = clusterId;
    }

    public long getNumberOfSpectra() {
        return numberOfSpectra;
    }

    public void setNumberOfSpectra(long numberOfSpectra) {
        this.numberOfSpectra = numberOfSpectra;
    }

    public List<String> getHighestRatioPepSequences() {
        return highestRatioPepSequences;
    }

    public void setHighestRatioPepSequences(List<String> highestRatioPepSequences) {
        this.highestRatioPepSequences = highestRatioPepSequences;
    }

    public List<String> getHighestRatioProteinAccessions() {
        return highestRatioProteinAccessions;
    }

    public void setHighestRatioProteinAccessions(List<String> highestRatioProteinAccessions) {
        this.highestRatioProteinAccessions = highestRatioProteinAccessions;
    }

    public double getMaxRatio() {
        return maxRatio;
    }

    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    public ClusterQuality getClusterQuality() {
        return clusterQuality;
    }

    public void setClusterQuality(ClusterQuality clusterQuality) {
        this.clusterQuality = clusterQuality;
    }

    public Map<String, List<String>> getProjectAssays() {
        return projectAssays;
    }

    public void setProjectAssays(Map<String, List<String>> projectAssays) {
        this.projectAssays = projectAssays;
    }
}
