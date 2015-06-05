package uk.ac.ebi.pride.cluster.search.service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import uk.ac.ebi.pride.cluster.search.model.SolrCluster;

import java.util.Set;

/**
 * @author ntoro
 * @since 06/04/15 19:43
 *
 */

public interface CustomSolrClusterRepository {

    /**
     * Return filtered clusters (or not) by modifications names and species names and peptides with the highlights for sequences
     * @param query (term to search by in "text" and in "highest ratio peptide sequences" fields ) mandatory
     * @param sequenceFilters (peptide sequences to filter by) optional
     * @param modNameFilters (modification names to filter by) optional
     * @param speciesNameFilters (species names to filter by) optional
     * @param sort (criterias to sort the results) optional
     * @param pageable requested page
     * @return A page with the clusters and the highlights snippets
     */
    HighlightPage<SolrCluster> findClusterWithHighlight(
            String query,
            Set<String> sequenceFilters,
            Set<String> modNameFilters,
            Set<String> speciesNameFilters,
            Sort sort,
            Pageable pageable);

    /**
     * Return filtered clusters (or not) by modifications names and species names and peptides with solr scores
     * @param query (term to search by in "text" and in "highest ratio peptide sequences" fields ) mandatory
     * @param sequenceFilters (peptide sequences to filter by) optional
     * @param modNameFilters (modification names to filter by) optional
     * @param speciesNameFilters (species names to filter by) optional
     * @param sort (criterias to sort the results) optional
     * @param pageable requested page
     * @return A page with the clusters and the highlights snippets
     */
    ScoredPage<SolrCluster> findClusterWithScore(
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
    FacetPage<SolrCluster> findClusterWithFacet(
            String query,
            Set<String> sequenceFilters,
            Set<String> modNameFilters,
            Set<String> speciesNameFilters);
}
