package uk.ac.ebi.pride.cluster.search.model;

/**
 * Created by IntelliJ IDEA.
 * User: noedelta
 * Date: 13/11/14
 * Time: 08:48
 */
public enum ClusterQuality {

    NOT_AVAILABLE("Not Available"),
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private final String name;

    private ClusterQuality(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
