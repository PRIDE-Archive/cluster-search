package uk.ac.ebi.pride.cluster.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
     * Return filtered clusters (or not) by modifications names and species names and peptides with the highlights for sequences
     * @param query (term to search by in "text" and in "highest ratio peptide sequences" fields ) mandatory
     * @param sequenceFilters (peptide sequences to filter by) optional
     * @param modNameFilters (modification names to filter by) optional
     * @param speciesNameFilters (species names to filter by) optional
     * @param sort (sorting order) optional
     * @param pageable requested page
     * @return A page with the clusters and the highlights snippets
     */
    PageWrapper<SolrCluster> findClusterByQuery(
            String query,
            Set<String> sequenceFilters,
            Set<String> modNameFilters,
            Set<String> speciesNameFilters,
            Sort sort,
            Pageable pageable);

    /**
     * Count the facets per modification name and species names
     * @param query (term to search by in text field and highest ratio peptide sequences) mandatory
     * @param sequenceFilters (peptide sequences to filter by) optional
     * @param modNameFilters (modification names to filter by) optional
     * @param speciesNameFilters (species names to filter by) optional
     * @return a map per facet field with the names and the number of hits per name
     */
    Map<String, Map<String, Long>> findClusterFacetByQuery(
            String query,
            Set<String> sequenceFilters,
            Set<String> modNameFilters,
            Set<String> speciesNameFilters);

    Page<SolrCluster> findByNearestPeaks(String quality, double precursorMz, double windowSize, double[] mzValues, double[] intensityValues, Pageable pageable);

    boolean existsCluster(Long clusterId);
}
