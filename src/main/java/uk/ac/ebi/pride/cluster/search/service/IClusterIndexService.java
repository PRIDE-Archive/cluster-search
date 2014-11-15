package uk.ac.ebi.pride.cluster.search.service;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.pride.cluster.search.model.Cluster;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: noedelta
 * Date: 15/11/14
 * Time: 13:22
 */
public interface IClusterIndexService {

    @Transactional
    void save(Cluster cluster);

    @Transactional
    void save(Iterable<Cluster> clusters);

    @Transactional
    void delete(long clusterId);

    @Transactional
    void delete(Cluster cluster);

    @Transactional
    void delete(List<Long> clusterIds);

    @Transactional
    void delete(Iterable<Cluster> clusters);

    @Transactional
    void deleteAll();

}
