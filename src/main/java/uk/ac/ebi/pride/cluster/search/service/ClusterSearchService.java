package uk.ac.ebi.pride.cluster.search.service;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.cluster.search.model.SolrCluster;
import uk.ac.ebi.pride.cluster.search.service.repository.SolrClusterRepository;
import uk.ac.ebi.pride.cluster.search.service.repository.SolrClusterSpectralSearchRepository;

import java.util.Set;

/**
 * @author Jose A Dianes <jdianes@ebi.ac.uk>
 * @author ntoro
 */

@Service
public class ClusterSearchService implements IClusterSearchService {

    private SolrClusterRepository solrClusterRepository;
    private SolrClusterSpectralSearchRepository solrClusterSpectralSearchRepository;

    public ClusterSearchService(SolrClusterRepository solrClusterRepository, SolrClusterSpectralSearchRepository solrClusterSpectralSearchRepository) {
        this.solrClusterRepository = solrClusterRepository;
        this.solrClusterSpectralSearchRepository = solrClusterSpectralSearchRepository;
    }

    @Override
    public Page<SolrCluster> findAll(Pageable pageable) {
        return this.solrClusterRepository.findAll(pageable);
    }

    @Override
    public SolrCluster findById(Long clusterId) {
        return solrClusterRepository.findOne(clusterId);
    }

    @Override
    public Page<SolrCluster> findByHighestRatioPepSequences(Set<String> sequences, Pageable pageable) {
        return solrClusterRepository.findByHighestRatioPepSequences(sequences, pageable);
    }

    @Override
    public Page<SolrCluster> findByNearestPeaks(double mz1, double intensity1, Pageable pageable) {
        return solrClusterSpectralSearchRepository.findByNearestPeaks(mz1, intensity1, pageable);
    }


    @Override
    public boolean existsCluster(Long clusterId){
        return solrClusterRepository.exists(clusterId);
    }


}
