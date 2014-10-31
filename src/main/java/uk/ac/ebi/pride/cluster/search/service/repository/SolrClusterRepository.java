package uk.ac.ebi.pride.cluster.search.service.repository;

import org.springframework.data.solr.repository.SolrCrudRepository;
import uk.ac.ebi.pride.cluster.search.model.Cluster;

/**
 * @author ntoro
 * @since 30/10/14 09:25
 */
public interface SolrClusterRepository extends SolrCrudRepository<Cluster, String> {
}
