package uk.ac.ebi.pride.cluster.search.service.repository;

import org.springframework.data.solr.repository.SolrCrudRepository;
import uk.ac.ebi.pride.cluster.search.model.Cluster;

/**
 * @author ntoro
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 *
 */
public interface SolrClusterRepository extends SolrCrudRepository<Cluster, Long> {

}
