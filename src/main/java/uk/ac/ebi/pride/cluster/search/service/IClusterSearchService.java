package uk.ac.ebi.pride.cluster.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.ac.ebi.pride.cluster.search.model.SolrCluster;
import uk.ac.ebi.pride.indexutils.results.PageWrapper;

import java.util.Map;
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

    /**
     * Return filtered clusters (or not) by modifications names with the highlights for sequences
     * @param sequences (peptide sequences to search) mandatory
     * @param modNameFilters (modification names to search) optional
     * @param pageable requested page
     * @return A page with the clusters and the highlights snippets
     */
    PageWrapper<SolrCluster> findByHighestRatioPepSequencesHighlightsOnModificationNames(Set<String> sequences, Set<String> modNameFilters, Pageable pageable);

    /**
     * Count the facets per modification name
     * @param sequences (peptide sequences to search) mandatory
     * @param modNameFilters (modification names to search) optional
     * @return a map with the mod_names and the number of hits per mod_synonym
     */
    Map<String, Long> findByHighestRatioPepSequencesFacetOnModificationNames(Set<String> sequences, Set<String> modNameFilters);

    Page<SolrCluster> findByNearestPeaks(String quality, double precursorMz, double windowSize, double[] mzValues, double[] intensityValues, Pageable pageable);

    boolean existsCluster(Long clusterId);
}
