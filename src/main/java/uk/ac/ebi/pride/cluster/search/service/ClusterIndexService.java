package uk.ac.ebi.pride.cluster.search.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.cluster.search.model.Cluster;
import uk.ac.ebi.pride.cluster.search.service.repository.SolrClusterRepository;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by jdianes on 04/11/2014.
 * @author ntoro
 */
@Service
public class ClusterIndexService {

    private static Logger logger = LoggerFactory.getLogger(ClusterIndexService.class);

    private SolrClusterRepository solrClusterRepository;

    public ClusterIndexService(SolrClusterRepository solrClusterRepository) {
        this.solrClusterRepository = solrClusterRepository;
    }

    public void setSolrClusterRepository(SolrClusterRepository solrClusterRepository) {
        this.solrClusterRepository = solrClusterRepository;
    }

    public void save(Cluster cluster){
        Collection<Cluster> clusterCollection = Collections.singletonList(cluster);
        save(clusterCollection);
    }

    public void save(Iterable<Cluster> clusters) {
        if (clusters != null && clusters.iterator().hasNext()) {
            solrClusterRepository.save(clusters);
        } else {
            logger.error("Cluster Index Service: Trying to save an empty cluster list!");
        }
    }

    public void deleteAll() {
        solrClusterRepository.deleteAll();
    }

    public void delete(Cluster cluster){
        solrClusterRepository.delete(cluster);
    }

    public void delete(Iterable<Cluster> clusters) {
        if (clusters != null && clusters.iterator().hasNext()) {
            solrClusterRepository.delete(clusters);
        } else {
            logger.info("No clusters to delete");

        }
    }

}
