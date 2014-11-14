package uk.ac.ebi.pride.cluster.search.model;

/**
 * User: noedelta
 * Date: 13/11/14
 * Time: 08:48
 */
public enum ClusterQuality {

    // The representation of the quality is the same used internally in solr (define in conf/enumsConfig)
    // The results will take into account the ordinal of the enum
    UNKNOWN,
    LOW,
    MEDIUM,
    HIGH

}
