package uk.ac.ebi.pride.cluster.search.service.repository;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.ac.ebi.pride.cluster.search.model.ClusterFields;
import uk.ac.ebi.pride.cluster.search.model.SolrCluster;

import java.util.List;

/**
 * @author jadianes
 */
public class SolrClusterSpectralSearchRepository implements IClusterSpectralSearchRepository {


    private static final String QUERY_DEF_TYPE = "edismax";
    private static final String EDISMAX_TIE_FACTOR = "0.8";  // 0.0 means that just the maximum scoring field is used. 1.0 means that all scoring fields are summed

    private SolrServer solrServer;

    public SolrClusterSpectralSearchRepository(SolrServer solrServer) {
        this.solrServer = solrServer;
    }

    @Override
    public Page<SolrCluster> findByNearestPeaks(String quality, double precursorMz, double windowSize, double[] mzValues, double[] intensityValues, final Pageable pageable) {

        // prepare the query
        SolrQuery query = prepareBasicQuery(quality, precursorMz, windowSize, mzValues, intensityValues);

        query.setStart(pageable.getPageNumber());
        query.setRows(pageable.getPageSize());

        // perform the query
        try {
            final QueryResponse rsp = solrServer.query(query);

            // To read Documents as beans, the bean must be annotated
            List<SolrCluster> clusters = rsp.getBeans(SolrCluster.class);

            return new SolrClusterPage(pageable,rsp.getResults(),clusters);

        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        return null;


    }


    private SolrQuery prepareBasicQuery(String quality, double precursorMz, double windowSize, double[] mzValues, double[] intensityValues) {
        // prepare the query
        SolrQuery query = getBasicEDismaxQuery(quality);
        query.setParam("bq", ClusterFields.AVG_PRECURSOR_MZ + ":[" + (precursorMz-windowSize) + " TO " + (precursorMz+windowSize) + "]");
        int i = 0;
        String boostString = new String("sum( ");
        for (double mzValue: mzValues) {
            boostString = boostString + "div(1, sqedist(" + mzValue + ", " + intensityValues[i] + ", consensus_spectrum_mz_mean_" + i + ", consensus_spectrum_intensity_mean_" + i + ")),";
            i++;
        }
        boostString = boostString.substring(0,boostString.length()-1) + ")";

        query.setParam("boost",
                boostString
        );

        return query;
    }

    public static SolrQuery getBasicEDismaxQuery(String queryString) {
        SolrQuery query = new SolrQuery();

        query.setParam("defType", QUERY_DEF_TYPE); // use DisMax query parser
        query.setParam("tie", EDISMAX_TIE_FACTOR); // 0.0 means that just the maximum scoring field is used. 1.0 means that all scoring fields are summed
        query.setParam("qf", ClusterFields.CLUSTER_QUALITY);
        query.setParam("q", queryString);

        return query;
    }

}
