package uk.ac.ebi.pride.cluster.search.service.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.cluster.search.model.ClusterFields;
import uk.ac.ebi.pride.cluster.search.model.SolrCluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ntoro
 * @since 06/04/15 19:44
 */
@Repository
public class SolrClusterRepositoryImpl implements CustomSolrClusterRepository {

    public static final int FACET_MIN_COUNT = 10000;
    private SolrTemplate solrTemplate;


    public SolrClusterRepositoryImpl(SolrTemplate solrTemplate) {
        this.solrTemplate = solrTemplate;
    }

    public HighlightPage<SolrCluster> findByTextAndHighestRatioPepSequencesFilterOnModificationNamesAndSpeciesNames(
            String query,
            Set<String> sequences,
            Set<String> modNameFilters,
            Set<String> speciesNameFilters,
            Pageable pageable) {

        Criteria conditions = createSearchConditions(query);
        List<FilterQuery> filterQueries = createFilterQuery(sequences, modNameFilters, speciesNameFilters);
        HighlightOptions highlightOptions = new HighlightOptions();

        highlightOptions.addField(ClusterFields.HIGHEST_RATIO_PEP_SEQUENCE);
        highlightOptions.setSimplePrefix(SolrClusterRepository.HIGHLIGHT_PRE_FRAGMENT);
        highlightOptions.setSimplePostfix(SolrClusterRepository.HIGHLIGHT_POST_FRAGMENT);

        SimpleHighlightQuery search = new SimpleHighlightQuery(conditions);
        if (filterQueries != null && !filterQueries.isEmpty()) {
            for (FilterQuery filterQuery : filterQueries) {
                search.addFilterQuery(filterQuery);
            }

        }
        search.setHighlightOptions(highlightOptions);
        search.setPageRequest(pageable);


        return solrTemplate.queryForHighlightPage(search, SolrCluster.class);
    }

    public FacetPage<SolrCluster> findByTextAndHighestRatioPepSequencesFacetOnModificationNamesAndSpeciesNames(
            String query,
            Set<String> sequenceFilters,
            Set<String> modNameFilters,
            Set<String> speciesNameFilters) {

        Criteria conditions = createSearchConditions(query);
        List<FilterQuery> filterQueries = createFilterQuery(sequenceFilters, modNameFilters, speciesNameFilters);
        FacetOptions facetOptions = new FacetOptions(ClusterFields.MOD_SYNONYMS, ClusterFields.SPECIES_NAMES);
        facetOptions.setFacetLimit(FACET_MIN_COUNT);

        SimpleFacetQuery search = new SimpleFacetQuery(conditions);
        if (filterQueries != null && !filterQueries.isEmpty()) {
            for (FilterQuery filterQuery : filterQueries) {
                search.addFilterQuery(filterQuery);
            }

        }
        search.setFacetOptions(facetOptions);
        search.setPageRequest(new PageRequest(0, 1)); //We are only interesting in the facets, not in the query result

        return solrTemplate.queryForFacetPage(search, SolrCluster.class);
    }

    private Criteria createSearchConditions(String searchTerms) {
        Criteria conditions = null;

        //Query
        if (searchTerms != null && !searchTerms.isEmpty()) {
            String[] words = searchTerms.split(" ");

            for (String word : words) {
                if (conditions == null) {
                    conditions = new Criteria(ClusterFields.TEXT).contains(word)
                            .or(new Criteria(ClusterFields.HIGHEST_RATIO_PEP_SEQUENCE).is(word));
                } else {
                    conditions = conditions.or(ClusterFields.TEXT).contains(word)
                            .or(new Criteria(ClusterFields.HIGHEST_RATIO_PEP_SEQUENCE).is(word));
                }
            }
        } else {
            //Default Criteria
            conditions = new Criteria(ClusterFields.TEXT).or(new Criteria(ClusterFields.HIGHEST_RATIO_PEP_SEQUENCE));
        }

        return conditions;
    }

    private List<FilterQuery> createFilterQuery(Set<String> sequences, Set<String> modNameFilters, Set<String> speciesNameFilters) {
        List<FilterQuery> filterQueries = new ArrayList<FilterQuery>();

        //Sequences filter
        createFilterCriteria(sequences, ClusterFields.HIGHEST_RATIO_PEP_SEQUENCE, filterQueries);

        //Modifications filter
        createFilterCriteria(modNameFilters, ClusterFields.MOD_SYNONYMS, filterQueries);

        //Species filter
        createFilterCriteria(speciesNameFilters, ClusterFields.SPECIES_NAMES, filterQueries);

        return filterQueries;
    }

    private void createFilterCriteria(Set<String> values, String field, List<FilterQuery> filterQueries) {

        if (values != null) {
            Criteria conditions = null;

            for (String value : values) {
                if (conditions == null) {
                    conditions = new Criteria(field).is(value);
                } else {
                    conditions = conditions.or(new Criteria(field).is(value));
                }
            }

            if (conditions != null) {
                filterQueries.add(new SimpleFilterQuery(conditions));
            }
        }
    }

}
