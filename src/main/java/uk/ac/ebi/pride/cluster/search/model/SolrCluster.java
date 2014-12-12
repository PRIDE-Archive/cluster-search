package uk.ac.ebi.pride.cluster.search.model;

import org.apache.solr.client.solrj.beans.Field;

import java.util.*;

import static uk.ac.ebi.pride.cluster.search.model.ClusterFields.*;

/**
 * @author ntoro
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 *
 */
public class SolrCluster {

    private static final java.lang.String ASSAYS_DELIMITEER = " ";
    public static final int NUM_LOW_RES_PEAKS = 4;

    @Field(ID)
    private long id;

    @Field(HIGHEST_RATIO_PEP_SEQUENCE)
    private List<String> highestRatioPepSequences;

    @Field(HIGHEST_RATIO_PROTEIN_ACCESSION)
    private List<String> highestRatioProteinAccessions;

    @Field(NUMBER_OF_SPECTRA)
    private long numberOfSpectra;

    @Field(MAX_RATIO)
    private double maxRatio;

    @Field(CLUSTER_QUALITY)
    private String clusterQuality;

    @Field(AVG_PRECURSOR_MZ)
    private double averagePrecursorMz;

    @Field(AVG_PRECURSOR_CHARGE)
    private double averagePrecursorCharge;

    // Represent the relationship between project and assays ("PXD0000001 1234 2345")
    // Not visible outside
    @Field(PROJECT_ASSAYS)
    private List<String> projectAssaysList;

    @Field(PROJECTS)
    private List<String> projects;

    @Field(SPECIES_NAMES)
    private List<String> speciesNames;

    @Field(SPECIES_ACCESSIONS)
    private List<String> speciesAccessions;

    @Field(SPECIES_ASCENDANTS_NAMES)
    private List<String> speciesAscendantsNames;

    @Field(SPECIES_ASCENDANTS_ACCESSIONS)
    private List<String> speciesAscendantsAccessions;

    @Field(CONSENSUS_SPECTRUM_MZ)
    private List<Double> consensusSpectrumMz;

    @Field(CONSENSUS_SPECTRUM_MZ_MEAN_1)
    private double consensusSpectrumMzMean1;

    @Field(CONSENSUS_SPECTRUM_MZ_MEAN_2)
    private double consensusSpectrumMzMean2;

    @Field(CONSENSUS_SPECTRUM_MZ_MEAN_3)
    private double consensusSpectrumMzMean3;

    @Field(CONSENSUS_SPECTRUM_MZ_MEAN_4)
    private double consensusSpectrumMzMean4;

    @Field(CONSENSUS_SPECTRUM_MZ_SEM)
    private double consensusSpectrumMzSem;

    @Field(CONSENSUS_SPECTRUM_INTENSITY)
    private List<Double> consensusSpectrumIntensity;

    @Field(CONSENSUS_SPECTRUM_INTENSITY_MEAN_1)
    private double consensusSpectrumIntensityMean1;

    @Field(CONSENSUS_SPECTRUM_INTENSITY_MEAN_2)
    private double consensusSpectrumIntensityMean2;

    @Field(CONSENSUS_SPECTRUM_INTENSITY_MEAN_3)
    private double consensusSpectrumIntensityMean3;

    @Field(CONSENSUS_SPECTRUM_INTENSITY_MEAN_4)
    private double consensusSpectrumIntensityMean4;

    @Field(CONSENSUS_SPECTRUM_INTENSITY_SEM)
    private double consensusSpectrumIntensitySem;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getClusterQuality() {
        return clusterQuality;
    }

    public void setClusterQuality(String clusterQuality) {
        this.clusterQuality = clusterQuality;
    }

    public double getAveragePrecursorMz() {
        return averagePrecursorMz;
    }

    public void setAveragePrecursorMz(double averagePrecursorMz) {
        this.averagePrecursorMz = averagePrecursorMz;
    }

    public double getAveragePrecursorCharge() {
        return averagePrecursorCharge;
    }

    public void setAveragePrecursorCharge(double averagePrecursorCharge) {
        this.averagePrecursorCharge = averagePrecursorCharge;
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

    public List<String> getSpeciesNames() {
        return speciesNames;
    }

    public void setSpeciesNames(List<String> speciesNames) {
        this.speciesNames = speciesNames;
    }

    public List<String> getSpeciesAccessions() {
        return speciesAccessions;
    }

    public void setSpeciesAccessions(List<String> speciesAccessions) {
        this.speciesAccessions = speciesAccessions;
    }

    public List<String> getSpeciesAscendantsNames() {
        return speciesAscendantsNames;
    }

    public void setSpeciesAscendantsNames(List<String> speciesAscendantsNames) {
        this.speciesAscendantsNames = speciesAscendantsNames;
    }

    public List<String> getSpeciesAscendantsAccessions() {
        return speciesAscendantsAccessions;
    }

    public void setSpeciesAscendantsAccessions(List<String> speciesAscendantsAccessions) {
        this.speciesAscendantsAccessions = speciesAscendantsAccessions;
    }

    public List<Double> getConsensusSpectrumMz() {
        return consensusSpectrumMz;
    }

    public void setConsensusSpectrumMz(List<Double> consensusSpectrumMz) {
        this.consensusSpectrumMz = consensusSpectrumMz;
    }

    public double getConsensusSpectrumMzMean1() {
        return consensusSpectrumMzMean1;
    }

    public void setConsensusSpectrumMzMean1(double consensusSpectrumMzMean) {
        this.consensusSpectrumMzMean1 = consensusSpectrumMzMean;
    }

    public double getConsensusSpectrumMzMean2() {
        return consensusSpectrumMzMean2;
    }

    public void setConsensusSpectrumMzMean2(double consensusSpectrumMzMean2) {
        this.consensusSpectrumMzMean2 = consensusSpectrumMzMean2;
    }

    public double getConsensusSpectrumMzMean3() {
        return consensusSpectrumMzMean3;
    }

    public void setConsensusSpectrumMzMean3(double consensusSpectrumMzMean3) {
        this.consensusSpectrumMzMean3 = consensusSpectrumMzMean3;
    }

    public double getConsensusSpectrumMzMean4() {
        return consensusSpectrumMzMean4;
    }

    public void setConsensusSpectrumMzMean4(double consensusSpectrumMzMean4) {
        this.consensusSpectrumMzMean4 = consensusSpectrumMzMean4;
    }

    public double getConsensusSpectrumMzSem() {
        return consensusSpectrumMzSem;
    }

    public void setConsensusSpectrumMzSem(double consensusSpectrumMzSem) {
        this.consensusSpectrumMzSem = consensusSpectrumMzSem;
    }

    public List<Double> getConsensusSpectrumIntensity() {
        return consensusSpectrumIntensity;
    }

    public void setConsensusSpectrumIntensity(List<Double> consensusSpectrumIntensity) {
        this.consensusSpectrumIntensity = consensusSpectrumIntensity;
    }

    public double[] getConsensusSpectrumIntensityMeans() {
        double[] consensusSpectrumIntensityMeans = new double[NUM_LOW_RES_PEAKS];

        consensusSpectrumIntensityMeans[0] = this.consensusSpectrumIntensityMean1;
        consensusSpectrumIntensityMeans[1] = this.consensusSpectrumIntensityMean2;
        consensusSpectrumIntensityMeans[2] = this.consensusSpectrumIntensityMean3;
        consensusSpectrumIntensityMeans[3] = this.consensusSpectrumIntensityMean4;

        return consensusSpectrumIntensityMeans;
    }

    public void setConsensusSpectrumIntensityMeans(double[] consensusSpectrumIntensityMeans) {
        if (consensusSpectrumIntensityMeans != null) {
            this.consensusSpectrumIntensityMean1 = consensusSpectrumIntensityMeans[0];
            this.consensusSpectrumIntensityMean2 = consensusSpectrumIntensityMeans[1];
            this.consensusSpectrumIntensityMean3 = consensusSpectrumIntensityMeans[2];
            this.consensusSpectrumIntensityMean4 = consensusSpectrumIntensityMeans[3];
        }
    }

    public double[] getConsensusSpectrumMzMeans() {
        double[] consensusSpectrumMzMeans = new double[NUM_LOW_RES_PEAKS];

        consensusSpectrumMzMeans[0] = this.consensusSpectrumMzMean1;
        consensusSpectrumMzMeans[1] = this.consensusSpectrumMzMean2;
        consensusSpectrumMzMeans[2] = this.consensusSpectrumMzMean3;
        consensusSpectrumMzMeans[3] = this.consensusSpectrumMzMean4;

        return consensusSpectrumMzMeans;
    }

    public void setConsensusSpectrumMzMeans(double[] consensusSpectrumMzMeans) {
        if (consensusSpectrumMzMeans != null) {
            this.consensusSpectrumMzMean1 = consensusSpectrumMzMeans[0];
            this.consensusSpectrumMzMean2 = consensusSpectrumMzMeans[1];
            this.consensusSpectrumMzMean3 = consensusSpectrumMzMeans[2];
            this.consensusSpectrumMzMean4 = consensusSpectrumMzMeans[3];
        }
    }

}
