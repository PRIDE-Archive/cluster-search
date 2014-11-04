package uk.ac.ebi.pride.cluster.search.service.repository;

import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.repository.support.SolrRepositoryFactory;

/**
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 *
 */
public class SolrClusterRepositoryFactory {
    private SolrOperations solrOperations;

    public SolrClusterRepositoryFactory(SolrOperations solrOperations) {
        this.solrOperations = solrOperations;
    }

    public SolrClusterRepository create() {
        return new SolrRepositoryFactory(this.solrOperations).getRepository(SolrClusterRepository.class);
    }
}
