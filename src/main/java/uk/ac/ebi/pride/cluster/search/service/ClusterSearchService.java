package uk.ac.ebi.pride.cluster.search.service;

import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.cluster.search.model.Cluster;
import uk.ac.ebi.pride.cluster.search.service.repository.SolrClusterRepository;

/**
 * Created by jdianes on 04/11/2014.
 * @author ntoro
 */

@Service
public class ClusterSearchService implements IClusterSearchService {

    private SolrClusterRepository solrClusterRepository;

    public ClusterSearchService(SolrClusterRepository solrClusterRepository) {
        this.solrClusterRepository = solrClusterRepository;
    }

    public void setSolrClusterRepository(SolrClusterRepository solrClusterRepository) {
        this.solrClusterRepository = solrClusterRepository;
    }

    // find by accession methods
    @Override
    public Cluster findById(Long clusterId) {
        return solrClusterRepository.findOne(clusterId);
    }

    @Override
    public boolean existsCluster(Long clusterId){
        return solrClusterRepository.exists(clusterId);
    }

}
