package uk.ac.ebi.pride.cluster.search.service;

import uk.ac.ebi.pride.cluster.search.model.Cluster;

/**
 * Created by IntelliJ IDEA.
 * User: noedelta
 * Date: 15/11/14
 * Time: 13:23
 */
public interface IClusterSearchService {
    // find by accession methods
    Cluster findById(Long id);

    boolean existsCluster(Long clusterId);
}
