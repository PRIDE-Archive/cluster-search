package uk.ac.ebi.pride.cluster.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.cluster.search.model.ClusterFields;
import uk.ac.ebi.pride.cluster.search.model.SolrCluster;
import uk.ac.ebi.pride.cluster.search.service.repository.SolrClusterRepository;
import uk.ac.ebi.pride.cluster.search.service.repository.SolrClusterSpectralSearchRepository;
import uk.ac.ebi.pride.indexutils.results.PageWrapper;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Jose A Dianes <jdianes@ebi.ac.uk>
 * @author ntoro
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
    public PageWrapper<SolrCluster> findByHighestRatioPepSequencesHighlightsOnModificationNames(
            Set<String> sequences, Set<String> modNameFilters, Pageable pageable) {

        PageWrapper<SolrCluster> clusters;

        if (modNameFilters == null || modNameFilters.isEmpty()) {
            clusters = new PageWrapper<SolrCluster>(solrClusterRepository.findByHighestRatioPepSequencesHighlights(sequences, pageable));
        } else
            clusters = new PageWrapper<SolrCluster>(solrClusterRepository.findByHighestRatioPepSequencesAndHighlightsAndFilterModNames(sequences, modNameFilters, pageable));

        return clusters;
    }

    @Override
    public Map<String, Long> findByHighestRatioPepSequencesFacetOnModificationNames(Set<String> sequences, Set<String> modNameFilters) {

        Map<String, Long> modificationsCount = new TreeMap<String, Long>();
        FacetPage<SolrCluster> clusters;

        if (modNameFilters == null || modNameFilters.isEmpty()) {
            clusters = solrClusterRepository.findByHighestRatioPepSequencesFacetModNames(sequences, new PageRequest(0, 1));
        } else
            clusters = solrClusterRepository.findByHighestRatioPepSequencesFacetAndFilterModNames(sequences, modNameFilters, new PageRequest(0, 1));


        if (clusters != null) {
            for (FacetFieldEntry facetFieldEntry : clusters.getFacetResultPage(ClusterFields.MOD_NAMES)) {
                modificationsCount.put(facetFieldEntry.getValue(), facetFieldEntry.getValueCount());
            }
        }
        return modificationsCount;
    }

    @Override
    public Page<SolrCluster> findByNearestPeaks(String quality, double precursorMz, double windowSize, double[] mzValues, double[] intensityValues, Pageable pageable) {
        assert(mzValues != null): "MZ values are needed";
        assert(intensityValues != null): "Intensity values are needed";
        assert(mzValues.length == intensityValues.length): "Same number of MZ and Intensity are needed";

        return solrClusterSpectralSearchRepository.findByNearestPeaks(quality, precursorMz, windowSize, mzValues, intensityValues, pageable);
    }


    @Override
    public boolean existsCluster(Long clusterId){
        return solrClusterRepository.exists(clusterId);
    }


}
