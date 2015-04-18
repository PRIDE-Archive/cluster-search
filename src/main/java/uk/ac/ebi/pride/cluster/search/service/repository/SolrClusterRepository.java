package uk.ac.ebi.pride.cluster.search.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.cluster.search.model.SolrCluster;

import java.util.Set;

import static uk.ac.ebi.pride.cluster.search.model.ClusterFields.HIGHEST_RATIO_PEP_SEQUENCE;

/**
 * @author ntoro
 * @author Jose A. Dianes <jadianes@gmail.com>
 *
 */

@Repository
public interface SolrClusterRepository extends  CustomSolrClusterRepository, SolrCrudRepository<SolrCluster, Long> {

    public static final String HIGHLIGHT_PRE_FRAGMENT = "<span class='search-hit'>";
    public static final String HIGHLIGHT_POST_FRAGMENT = "</span>";

    //Redundant: we can use the one with highlights
    @Query(value = HIGHEST_RATIO_PEP_SEQUENCE + ":(?0)")
    Page<SolrCluster> findByHighestRatioPepSequences(Set<String> sequences, Pageable pageable);

//    //Highlighting
//    @Query(value = HIGHEST_RATIO_PEP_SEQUENCE + ":(?0)")
//    @Highlight(prefix = HIGHLIGHT_PRE_FRAGMENT, postfix = HIGHLIGHT_POST_FRAGMENT, fields = {HIGHEST_RATIO_PEP_SEQUENCE})
//    HighlightPage<SolrCluster> findByHighestRatioPepSequencesHighlights(Set<String> sequences, Pageable pageable);
//
//    //Faceting
//    @Query(value = HIGHEST_RATIO_PEP_SEQUENCE + ":(?0)", defaultOperator = AND)
//    @Facet(fields = {MOD_NAMES, SPECIES_NAMES}, limit = 100)  //default is 10 it can be reached  with modifications and labels
//    FacetPage<SolrCluster> findByHighestRatioPepSequencesFacetModNamesAndSpeciesNames(Set<String> sequences, Pageable pageable);

//    //Mod names
//    //Filter by mods
//    //Redundant: we can use the one with highlights
//    @Query(value = HIGHEST_RATIO_PEP_SEQUENCE + ":(?0)", filters = MOD_NAMES + ":(?1)", defaultOperator = AND)
//    Page<SolrCluster> findByHighestRatioPepSequencesAndFilterModNames(Set<String> sequences, Set<String> modNames, Pageable pageable);
//
//    //Highlighting
//    @Query(value = HIGHEST_RATIO_PEP_SEQUENCE + ":(?0)", filters = MOD_NAMES + ":(?1)", defaultOperator = AND)
//    @Highlight(prefix = HIGHLIGHT_PRE_FRAGMENT, postfix = HIGHLIGHT_POST_FRAGMENT, fields = {HIGHEST_RATIO_PEP_SEQUENCE})
//    HighlightPage<SolrCluster> findByHighestRatioPepSequencesAndHighlightsAndFilterModNames(Set<String> sequences, Set<String> modNames, Pageable pageable);
//
//    //Faceting
//    @Query(value = HIGHEST_RATIO_PEP_SEQUENCE + ":(?0)", filters = MOD_NAMES + ":(?1)", defaultOperator = AND)
//    @Facet(fields = {MOD_NAMES, SPECIES_NAMES}, limit = 100)  //default is 10 it can be reached  with modifications and labels
//    FacetPage<SolrCluster> findByHighestRatioPepSequencesFacetModNamesAndSpeciesNamesAndFilterModNames(Set<String> sequences, Set<String> modNames, Pageable pageable);


//    //Species names
//    //Filter by mods
//    //Redundant: we can use the one with highlights
//    @Query(value = HIGHEST_RATIO_PEP_SEQUENCE + ":(?0)", filters = SPECIES_NAMES + ":(?1)", defaultOperator = AND)
//    Page<SolrCluster> findByHighestRatioPepSequencesAndFilterSpeciesNames(Set<String> sequences, Set<String> speciesNames, Pageable pageable);
//
//    //Highlighting
//    @Query(value = HIGHEST_RATIO_PEP_SEQUENCE + ":(?0)", filters = SPECIES_NAMES + ":(?1)", defaultOperator = AND)
//    @Highlight(prefix = HIGHLIGHT_PRE_FRAGMENT, postfix = HIGHLIGHT_POST_FRAGMENT, fields = {HIGHEST_RATIO_PEP_SEQUENCE})
//    HighlightPage<SolrCluster> findByHighestRatioPepSequencesAndHighlightsAndFilterSpeciesNames(Set<String> sequences, Set<String> speciesNames, Pageable pageable);
//
//    //Faceting
//    @Query(value = HIGHEST_RATIO_PEP_SEQUENCE + ":(?0)", filters = SPECIES_NAMES + ":(?1)", defaultOperator = AND)
//    @Facet(fields = {MOD_NAMES, SPECIES_NAMES}, limit = 100)  //default is 10 it can be reached  with modifications and labels
//    FacetPage<SolrCluster> findByHighestRatioPepSequencesFacetModNamesAndSpeciesNamesAndFilterSpeciesNames(Set<String> sequences, Set<String> speciesNames, Pageable pageable);


//    //Mod names and species names
//    //Filter by mods
//    //Redundant: we can use the one with highlights
//    @Query(value = HIGHEST_RATIO_PEP_SEQUENCE + ":(?0)", filters = {MOD_NAMES + ":(?1)", SPECIES_NAMES + ":(?2)"}, defaultOperator = AND)
//    Page<SolrCluster> findByHighestRatioPepSequencesAndFilterModNamesAndSpeciesNames(Set<String> sequences, Set<String> modNames, Set<String> speciesNames, Pageable pageable);
//
//    //Highlighting
//    @Query(value = HIGHEST_RATIO_PEP_SEQUENCE + ":(?0)", filters = {MOD_NAMES + ":(?1)", SPECIES_NAMES + ":(?2)"}, defaultOperator = AND)
//    @Highlight(prefix = HIGHLIGHT_PRE_FRAGMENT, postfix = HIGHLIGHT_POST_FRAGMENT, fields = {HIGHEST_RATIO_PEP_SEQUENCE})
//    HighlightPage<SolrCluster> findByHighestRatioPepSequencesAndHighlightsAndFilterModNamesAndSpeciesNames(Set<String> sequences, Set<String> modNames, Set<String> speciesNames, Pageable pageable);
//
//    //Faceting
//    @Query(value = HIGHEST_RATIO_PEP_SEQUENCE + ":(?0)", filters = {MOD_NAMES + ":(?1)", SPECIES_NAMES + ":(?2)"}, defaultOperator = AND)
//    @Facet(fields = {MOD_NAMES, SPECIES_NAMES}, limit = 100)  //default is 10 it can be reached  with modifications and labels
//    FacetPage<SolrCluster> findByHighestRatioPepSequencesFacetModNamesAndSpeciesNamesAndFilterModNamesAndSpeciesNames(Set<String> sequences, Set<String> modNames, Set<String> speciesNames, Pageable pageable);


}
