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
 * @author Jose A. Dianes <jadianes@gmail.com>
 *
 */

@Repository
public interface SolrClusterRepository extends SolrCrudRepository<SolrCluster, Long> {

    Page<SolrCluster> findAll(Pageable pageable);

    @Query("highest_ratio_pep_sequences:(?0)")
    Page<SolrCluster> findByHighestRatioPepSequences(Set<String> sequences, Pageable pageable);


//    @Query(boost = { "div(1,sqedst(?0,?1,consensus_spectrum_mz_mean_1:, consensus_spectrum_intensity_mean_1))"})
//    Page<SolrCluster> findByNearestPeaks(double mz1, double intensity1, Pageable pageable);

}
