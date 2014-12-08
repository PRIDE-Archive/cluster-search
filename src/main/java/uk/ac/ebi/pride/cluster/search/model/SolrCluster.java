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

    @Field(CONSENSUS_SPECTRUM_INTENSITY)
    private List<Double> consensusSpectrumIntensity;

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

    public List<Double> getConsensusSpectrumIntensity() {
        return consensusSpectrumIntensity;
    }

    public void setConsensusSpectrumIntensity(List<Double> consensusSpectrumIntensity) {
        this.consensusSpectrumIntensity = consensusSpectrumIntensity;
    }
}
