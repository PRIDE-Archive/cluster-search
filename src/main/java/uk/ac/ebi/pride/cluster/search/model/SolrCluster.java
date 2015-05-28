package uk.ac.ebi.pride.cluster.search.model;

import org.apache.solr.client.solrj.beans.Field;
import uk.ac.ebi.pride.archive.dataprovider.identification.ModificationProvider;
import uk.ac.ebi.pride.indexutils.helpers.ModificationHelper;

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
    private String highestRatioPepSequence;

    @Field(HIGHEST_RATIO_PROTEIN_ACCESSION)
    private List<String> highestRatioProteinAccessions;

    @Field(NUMBER_OF_SPECTRA)
    private long numberOfSpectra;

    @Field(TOTAL_NUMBER_OF_SPECTRA)
    private long totalNumberOfSpectra;

    @Field(NUMBER_OF_PROJECTS)
    private long numberOfProjects;

    @Field(TOTAL_NUMBER_OF_PROJECTS)
    private long totalNumberOfProjects;

    @Field(NUMBER_OF_SPECIES)
    private long numberOfSpecies;

    @Field(TOTAL_NUMBER_OF_SPECIES)
    private long totalNumberOfSpecies;

    @Field(NUMBER_OF_MODIFICATIONS)
    private long numberOfModifications;

    @Field(TOTAL_NUMBER_OF_MODIFICATIONS)
    private long totalNumberOfModifications;

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

    @Field(MODIFICATIONS)
    private List<String> modificationsAsString;

    @Field(MOD_NAMES)
    private List<String> modificationNames;

    @Field(MOD_ACCESSIONS)
    private List<String> modificationAccessions;

    @Field(CONSENSUS_SPECTRUM_MZ)
    private List<Double> consensusSpectrumMz;

    @Field(CONSENSUS_SPECTRUM_MZ_SEM)
    private double consensusSpectrumMzSem;

    @Field(CONSENSUS_SPECTRUM_INTENSITY)
    private List<Double> consensusSpectrumIntensity;

    @Field(CONSENSUS_SPECTRUM_INTENSITY_SEM)
    private double consensusSpectrumIntensitySem;

    @Field(CONSENSUS_SPECTRUM_MZ_MEAN)
    private Map<String, Double> consensusSpectrumMzMeans = new HashMap<String, Double>();

    @Field(CONSENSUS_SPECTRUM_INTENSITY_MEAN)
    private Map<String, Double> consensusSpectrumIntensityMeans = new HashMap<String, Double>();

    //ReadOnly
    @Field(TEXT)
    private List<String> text;

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

    public long getTotalNumberOfSpectra() {
        return totalNumberOfSpectra;
    }

    public void setTotalNumberOfSpectra(long totalNumberOfSpectra) {
        this.totalNumberOfSpectra = totalNumberOfSpectra;
    }

    public long getNumberOfProjects() {
        return numberOfProjects;
    }

    public void setNumberOfProjects(long numberOfProjects) {
        this.numberOfProjects = numberOfProjects;
    }

    public long getTotalNumberOfProjects() {
        return totalNumberOfProjects;
    }

    public void setTotalNumberOfProjects(long totalNumberOfProjects) {
        this.totalNumberOfProjects = totalNumberOfProjects;
    }

    public long getNumberOfSpecies() {
        return numberOfSpecies;
    }

    public void setNumberOfSpecies(long numberOfSpecies) {
        this.numberOfSpecies = numberOfSpecies;
    }

    public long getTotalNumberOfSpecies() {
        return totalNumberOfSpecies;
    }

    public void setTotalNumberOfSpecies(long totalNumberOfSpecies) {
        this.totalNumberOfSpecies = totalNumberOfSpecies;
    }

    public long getNumberOfModifications() {
        return numberOfModifications;
    }

    public void setNumberOfModifications(long numberOfModifications) {
        this.numberOfModifications = numberOfModifications;
    }

    public long getTotalNumberOfModifications() {
        return totalNumberOfModifications;
    }

    public void setTotalNumberOfModifications(long totalNumberOfModifications) {
        this.totalNumberOfModifications = totalNumberOfModifications;
    }

    public String getHighestRatioPepSequence() {
        return highestRatioPepSequence;
    }

    public void setHighestRatioPepSequence(String highestRatioPepSequence) {
        this.highestRatioPepSequence = highestRatioPepSequence;
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

    public Iterable<ModificationProvider> getModifications() {

            List<ModificationProvider> modifications = new ArrayList<ModificationProvider>();

            if (modificationsAsString != null) {
                for (String mod : modificationsAsString) {
                    if(!mod.isEmpty()) {
                        modifications.add(ModificationHelper.convertFromString(mod));
                    }
                }
            }

            return modifications;
        }

        public void setModifications(List<ModificationProvider> modifications) {

            if (modifications == null )
                return;

            List<String> modificationsAsString = new ArrayList<String>();
            List<String> modificationNames = new ArrayList<String>();
            List<String> modificationAccessions = new ArrayList<String>();

            for (ModificationProvider modification : modifications) {
                modificationsAsString.add(ModificationHelper.convertToString(modification));
                modificationAccessions.add(modification.getAccession());
                modificationNames.add(modification.getName());
            }

            this.modificationsAsString = modificationsAsString;
            this.modificationAccessions = modificationAccessions;
            this.modificationNames = modificationNames;
        }

        public void addModification(ModificationProvider modification) {

            if (modificationsAsString == null) {
                modificationsAsString = new ArrayList<String>();
            }

            if (modificationAccessions == null) {
                modificationAccessions = new ArrayList<String>();
            }

            if (modificationNames == null) {
                modificationNames = new ArrayList<String>();
            }

            if (modification != null) {
                modificationsAsString.add(ModificationHelper.convertToString(modification));
                modificationAccessions.add(modification.getAccession());
                modificationNames.add(modification.getName());
            }
        }


    public List<Double> getConsensusSpectrumMz() {
        return consensusSpectrumMz;
    }

    public void setConsensusSpectrumMz(List<Double> consensusSpectrumMz) {
        this.consensusSpectrumMz = consensusSpectrumMz;
    }

    public double[] getConsensusSpectrumMzMeans() {
        double[] res = new double[this.consensusSpectrumMzMeans.size()];

        Iterator<Double> it = this.consensusSpectrumMzMeans.values().iterator();
        int i=0;
        while (it.hasNext()) {
            res[i] = it.next();
            i++;
        }

        return res;
    }

    public void setConsensusSpectrumMzMeans(double[] consensusSpectrumMzMeans) {
        if (consensusSpectrumMzMeans != null) {
            for (int i = 0; i < consensusSpectrumMzMeans.length; i++) {
                this.consensusSpectrumMzMeans.put(
                        ClusterFields.CONSENSUS_SPECTRUM_MZ_MEAN.replace("*", ""+i),
                        consensusSpectrumMzMeans[i]
                );
            }
        }
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

        double[] res = new double[this.consensusSpectrumIntensityMeans.size()];

        Iterator<Double> it = this.consensusSpectrumIntensityMeans.values().iterator();
        int i=0;
        while (it.hasNext()) {
            res[i] = it.next();
            i++;
        }

        return res;
    }

    public void setConsensusSpectrumIntensityMeans(double[] consensusSpectrumIntensityMeans) {
        if (consensusSpectrumIntensityMeans != null) {
            for (int i = 0; i < consensusSpectrumIntensityMeans.length; i++) {
                this.consensusSpectrumIntensityMeans.put(
                        ClusterFields.CONSENSUS_SPECTRUM_INTENSITY_MEAN.replace("*", ""+i),
                        consensusSpectrumIntensityMeans[i]
                );
            }
        }
    }

    public double getConsensusSpectrumIntensitySem() {
        return consensusSpectrumIntensitySem;
    }

    public void setConsensusSpectrumIntensitySem(double consensusSpectrumIntensitySem) {
        this.consensusSpectrumIntensitySem = consensusSpectrumIntensitySem;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }
}
