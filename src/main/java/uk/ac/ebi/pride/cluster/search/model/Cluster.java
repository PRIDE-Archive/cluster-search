package uk.ac.ebi.pride.cluster.search.model;

import org.apache.solr.client.solrj.beans.Field;

import java.util.*;

import static uk.ac.ebi.pride.cluster.search.model.ClusterFields.*;

/**
 * @author ntoro
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 *
 */
public class Cluster {

    private static final java.lang.String ASSAYS_DELIMITEER = " ";
    @Field(ID)
    private long clusterId;

    @Field(HIGHEST_RATIO_PEP_SEQUENCE)
    private List<String> highestRatioPepSequences;

    @Field(HIGHEST_RATIO_PROTEIN_ACCESSION)
    private List<String> highestRatioProteinAccessions;

    @Field(NUMBER_OF_SPECTRA)
    private long numberOfSpectra;

    @Field(MAX_RATIO)
    private double maxRatio;

    @Field(CLUSTER_QUALITY)
    private ClusterQuality clusterQuality;

    @Field(AVG_PRECURSOR_MZ)
    private double avgPrecursorMz;

    @Field(AVG_PRECURSOR_CHARGE)
    private double avgPrecursorCharge;

    // Represent the relationship between project and assays ("PXD0000001 1234 2345")
    // Not visible outside
    @Field(PROJECT_ASSAYS)
    private List<String> projectAssaysList;

    @Field(PROJECTS)
    private List<String> projects;

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

    public double getAvgPrecursorMz() {
        return avgPrecursorMz;
    }

    public void setAvgPrecursorMz(double avgPrecursorMz) {
        this.avgPrecursorMz = avgPrecursorMz;
    }

    public double getAvgPrecursorCharge() {
        return avgPrecursorCharge;
    }

    public void setAvgPrecursorCharge(double avgPrecursorCharge) {
        this.avgPrecursorCharge = avgPrecursorCharge;
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public Map<String, List<String>> getProjectAssays() {

        Map<String, List<String>> projectAssays = new TreeMap<String, List<String>>();

        String[] tokens;
        for (String list : projectAssaysList) {
            tokens = list.trim().split(ASSAYS_DELIMITEER);
            if(tokens.length >= 2){
                projectAssays.put(tokens[0], Arrays.asList(Arrays.copyOfRange(tokens, 1, tokens.length)));
            }
        }

        return projectAssays;
    }

    public void setProjectAssays(Map<String, List<String>> projectAssays) {
        if(projectAssaysList == null ){
            projectAssaysList = new ArrayList<String>();
        }
        for (String projectAccession : projectAssays.keySet()) {
            StringBuilder stringBuilder = new StringBuilder(projectAccession);
            for (String assay : projectAssays.get(projectAccession)) {
                stringBuilder.append(ASSAYS_DELIMITEER).append(assay);
            }
            projectAssaysList.add(stringBuilder.toString());
        }
    }
}
