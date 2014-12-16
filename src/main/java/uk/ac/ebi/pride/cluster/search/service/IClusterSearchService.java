package uk.ac.ebi.pride.cluster.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.ac.ebi.pride.cluster.search.model.SolrCluster;

import java.util.Set;

/**
 * @author ntoro
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 *
 */
public interface IClusterSearchService {

    Page<SolrCluster> findAll(Pageable pageable);

    SolrCluster findById(Long id);

    Page<SolrCluster> findByHighestRatioPepSequences(Set<String> sequences, Pageable pageable);
    Page<SolrCluster> findByNearestPeaks(String quality, double precursorMz, double windowSize, double[] mzValues, double[] intensityValues, Pageable pageable);

    boolean existsCluster(Long clusterId);
}
