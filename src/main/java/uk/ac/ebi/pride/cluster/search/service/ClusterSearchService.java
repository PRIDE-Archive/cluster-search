package uk.ac.ebi.pride.cluster.search.service;

import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.cluster.search.model.Cluster;
import uk.ac.ebi.pride.cluster.search.service.repository.SolrClusterRepository;

/**
 * Created by jdianes on 04/11/2014.
 * @author ntoro
 */

@Service
public class ClusterSearchService {

    private SolrClusterRepository solrClusterRepository;

    public ClusterSearchService(SolrClusterRepository solrClusterRepository) {
        this.solrClusterRepository = solrClusterRepository;
    }

    public void setSolrClusterRepository(SolrClusterRepository solrClusterRepository) {
        this.solrClusterRepository = solrClusterRepository;
    }

    // find by accession methods
    public Cluster findById(Long id) {
        return solrClusterRepository.findOne(id);
    }

}
