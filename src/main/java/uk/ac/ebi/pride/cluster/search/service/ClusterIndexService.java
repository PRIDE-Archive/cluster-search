package uk.ac.ebi.pride.cluster.search.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.pride.cluster.search.model.Cluster;
import uk.ac.ebi.pride.cluster.search.service.repository.SolrClusterRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by jdianes on 04/11/2014.
 *
 * @author ntoro
 */
@Service
public class ClusterIndexService implements IClusterIndexService {

    private static Logger logger = LoggerFactory.getLogger(ClusterIndexService.class);

    private SolrClusterRepository solrClusterRepository;

    public ClusterIndexService(SolrClusterRepository solrClusterRepository) {
        this.solrClusterRepository = solrClusterRepository;
    }

    public void setSolrClusterRepository(SolrClusterRepository solrClusterRepository) {
        this.solrClusterRepository = solrClusterRepository;
    }

    @Override
    @Transactional
    public void save(Cluster cluster) {
        Collection<Cluster> clusterCollection = Collections.singletonList(cluster);
        save(clusterCollection);
    }

    @Override
    @Transactional
    public void save(Iterable<Cluster> clusters) {
        if (clusters != null && clusters.iterator().hasNext()) {
            solrClusterRepository.save(clusters);
        } else {
            logger.error("Cluster Index Service: Trying to save an empty cluster list!");
        }
    }

    @Override
    @Transactional
    public void delete(long clusterId) {
        solrClusterRepository.delete(clusterId);
    }

    @Override
    @Transactional
    public void delete(Cluster cluster) {
        if(cluster != null){
            solrClusterRepository.delete(cluster);
        }  else {
            logger.info("No cluster to delete");
        }
    }

    @Override
    @Transactional
    public void delete(List<Long> clusterIds) {
        Iterable<Cluster> clusters = solrClusterRepository.findAll(clusterIds);
        delete(clusters);
    }

    @Override
    @Transactional
    public void delete(Iterable<Cluster> clusters) {
        if (clusters != null && clusters.iterator().hasNext()) {
            solrClusterRepository.delete(clusters);
        } else {
            logger.info("No clusters to delete");
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        solrClusterRepository.deleteAll();
    }


}
