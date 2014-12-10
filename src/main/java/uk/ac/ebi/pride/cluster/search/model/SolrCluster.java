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

    @Field(ID)
    private long id;

    @Field(HIGHEST_RATIO_PEP_SEQUENCE)
    private Set<String> highestRatioPepSequences;

    @Field(HIGHEST_RATIO_PROTEIN_ACCESSION)
    private Set<String> highestRatioProteinAccessions;

    @Field(NUMBER_OF_SPECTRA)
    private long numberOfSpectra;

    @Field(MAX_RATIO)
    private double maxRatio;

    @Field(CLUSTER_QUALITY)
    private ClusterQuality clusterQuality;

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

    public Set<String> getHighestRatioPepSequences() {
        return highestRatioPepSequences;
    }

    public void setHighestRatioPepSequences(Set<String> highestRatioPepSequences) {
        this.highestRatioPepSequences = highestRatioPepSequences;
    }

    public Set<String> getHighestRatioProteinAccessions() {
        return highestRatioProteinAccessions;
    }

    public void setHighestRatioProteinAccessions(Set<String> highestRatioProteinAccessions) {
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

    public double getConsensusSpectrumIntensityMean1() {
        return consensusSpectrumIntensityMean1;
    }

    public void setConsensusSpectrumIntensityMean1(double consensusSpectrumIntensityMean1) {
        this.consensusSpectrumIntensityMean1 = consensusSpectrumIntensityMean1;
    }

    public double getConsensusSpectrumIntensityMean2() {
        return consensusSpectrumIntensityMean2;
    }

    public void setConsensusSpectrumIntensityMean2(double consensusSpectrumIntensityMean2) {
        this.consensusSpectrumIntensityMean2 = consensusSpectrumIntensityMean2;
    }

    public double getConsensusSpectrumIntensityMean3() {
        return consensusSpectrumIntensityMean3;
    }

    public void setConsensusSpectrumIntensityMean3(double consensusSpectrumIntensityMean3) {
        this.consensusSpectrumIntensityMean3 = consensusSpectrumIntensityMean3;
    }

    public double getConsensusSpectrumIntensityMean4() {
        return consensusSpectrumIntensityMean4;
    }

    public void setConsensusSpectrumIntensityMean4(double consensusSpectrumIntensityMean4) {
        this.consensusSpectrumIntensityMean4 = consensusSpectrumIntensityMean4;
    }

    public double getConsensusSpectrumIntensitySem() {
        return consensusSpectrumIntensitySem;
    }

    public void setConsensusSpectrumIntensitySem(double consensusSpectrumIntensitySem) {
        this.consensusSpectrumIntensitySem = consensusSpectrumIntensitySem;
    }
}
