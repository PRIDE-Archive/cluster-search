package uk.ac.ebi.pride.cluster.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.cluster.search.model.ClusterFields;
import uk.ac.ebi.pride.cluster.search.model.SolrCluster;
import uk.ac.ebi.pride.cluster.search.service.repository.SolrClusterRepository;
import uk.ac.ebi.pride.cluster.search.service.repository.SolrClusterSpectralSearchRepository;
import uk.ac.ebi.pride.indexutils.results.PageWrapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Jose A Dianes <jdianes@ebi.ac.uk>
 * @author ntoro <ntoro@ebi.ac.uk>
 */

@Service
public class ClusterSearchService implements IClusterSearchService {

    private SolrClusterRepository solrClusterRepository;
    private SolrClusterSpectralSearchRepository solrClusterSpectralSearchRepository;


    public ClusterSearchService(SolrClusterRepository solrClusterRepository, SolrClusterSpectralSearchRepository solrClusterSpectralSearchRepository) {
        this.solrClusterRepository = solrClusterRepository;
        this.solrClusterSpectralSearchRepository = solrClusterSpectralSearchRepository;
    }

    @Override
    public Page<SolrCluster> findAll(Pageable pageable) {
        return this.solrClusterRepository.findAll(pageable);
    }

    @Override
    public SolrCluster findById(Long clusterId) {
        return solrClusterRepository.findOne(clusterId);
    }

    @Override
    public Page<SolrCluster> findByHighestRatioPepSequences(Set<String> sequences, Pageable pageable) {
        return solrClusterRepository.findByHighestRatioPepSequences(sequences, pageable);
    }

    @Override
    public PageWrapper<SolrCluster> findByTextAndHighestRatioPepSequencesHighlightsFilterOnModificationNamesAndSpeciesNames(
            String query,
            Set<String> sequenceFilters,
            Set<String> modNameFilters,
            Set<String> speciesNameFilters,
            Pageable pageable) {
        return new PageWrapper<SolrCluster>(solrClusterRepository.findByTextAndHighestRatioPepSequencesFilterOnModificationNamesAndSpeciesNames(
                query,
                sequenceFilters,
                modNameFilters,
                speciesNameFilters,
                pageable)
        );
    }

    @Override
    public Map<String, Map<String, Long>> findByTextAndHighestRatioPepSequencesFacetOnModificationNamesAndSpeciesNames(
            String query,
            Set<String> sequenceFilters,
            Set<String> modNameFilters,
            Set<String> speciesNameFilters) {

        Map<String, Map<String, Long>> facets = new HashMap<String, Map<String, Long>>();

        FacetPage<SolrCluster> clusters;

        clusters = solrClusterRepository.findByTextAndHighestRatioPepSequencesFacetOnModificationNamesAndSpeciesNames(
                query,
                sequenceFilters,
                modNameFilters,
                speciesNameFilters);

        if (clusters != null) {
            //Species
            Map<String, Long> species = new LinkedHashMap<String, Long>();  //InsertionOrdered

            for (FacetFieldEntry facetFieldEntry : clusters.getFacetResultPage(ClusterFields.SPECIES_NAMES)) {
                species.put(facetFieldEntry.getValue(), facetFieldEntry.getValueCount());
            }
            if (!species.isEmpty()) {
                facets.put(ClusterFields.SPECIES_NAMES, species);
            }

            //Modifications
            Map<String, Long> modifications = new LinkedHashMap<String, Long>();  //InsertionOrdered
            for (FacetFieldEntry facetFieldEntry : clusters.getFacetResultPage(ClusterFields.MOD_NAMES)) {
                modifications.put(facetFieldEntry.getValue(), facetFieldEntry.getValueCount());
            }
            if (!modifications.isEmpty()) {
                facets.put(ClusterFields.MOD_NAMES, modifications);
            }
        }

        return facets;

    }


    @Override
    public Page<SolrCluster> findByNearestPeaks(String quality, double precursorMz, double windowSize, double[] mzValues, double[] intensityValues, Pageable pageable) {
        assert (mzValues != null) : "MZ values are needed";
        assert (intensityValues != null) : "Intensity values are needed";
        assert (mzValues.length == intensityValues.length) : "Same number of MZ and Intensity are needed";

        return solrClusterSpectralSearchRepository.findByNearestPeaks(quality, precursorMz, windowSize, mzValues, intensityValues, pageable);
    }


    @Override
    public boolean existsCluster(Long clusterId) {
        return solrClusterRepository.exists(clusterId);
    }


}
