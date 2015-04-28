package uk.ac.ebi.pride.cluster.search.service.repository;

import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.support.SolrRepositoryFactory;

/**
 * @author Jose A. Dianes <jadianes@gmail.com>
 *
 */
public class SolrClusterRepositoryFactory {
    private SolrTemplate solrTemplate;

    public SolrClusterRepositoryFactory(SolrTemplate solrTemplate) {
        this.solrTemplate = solrTemplate;
    }

    public SolrClusterRepository create() {
        return new SolrRepositoryFactory(this.solrTemplate).getRepository(
                SolrClusterRepository.class,
                new SolrClusterRepositoryImpl(this.solrTemplate));
    }
}
