package uk.ac.ebi.pride.cluster.search.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.cluster.search.model.SolrCluster;

import java.util.Set;

/**
 * @author ntoro
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 *
 */

@Repository
public interface SolrClusterRepository extends SolrCrudRepository<SolrCluster, Long> {

    Page<SolrCluster> findAll(Pageable pageable);

    @Query("highest_ratio_pep_sequences:(?0)")
    Page<SolrCluster> findByHighestRatioPepSequences(Set<String> sequences, Pageable pageable);


}
