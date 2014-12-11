package uk.ac.ebi.pride.cluster.search.service.repository;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.*;
import uk.ac.ebi.pride.cluster.search.model.SolrCluster;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author jadianes
 */
public class SolrClusterSpectralSearchRepository implements IClusterSpectralSearchRepository {


    private static final String QUERY_DEF_TYPE = "edismax";
    private static final String EDISMAX_TIE_FACTOR = "0.8";  // 0.0 means that just the maximum scoring field is used. 1.0 means that all scoring fields are summed

    private SolrServer projectServer;

    public SolrClusterSpectralSearchRepository(SolrServer projectServer) {
        this.projectServer = projectServer;
    }

    @Override
    public Page<SolrCluster> findByNearestPeaks(double mz1, double intensity1, final Pageable pageable) {

        // prepare the query
        SolrQuery query = prepareBasicQuery(mz1,intensity1);

        query.setStart(pageable.getPageNumber());
        query.setRows(pageable.getPageSize());

        // perform the query
        try {
            // Query the projectServer
            final QueryResponse rsp = projectServer.query(query);
            // get the results
            // To read Documents as beans, the bean must be annotated
            List<SolrCluster> clusters = rsp.getBeans(SolrCluster.class);


            return new SolrClusterPage(pageable,rsp.getResults(),clusters);

        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        return null;


    }


    private SolrQuery prepareBasicQuery(double mz1, double intensity1) {
        // prepare the query
        SolrQuery query = getBasicEDismaxQuery();
        query.setParam("boost",
                "div(1, sqedist(250, 200, consensus_spectrum_mz_mean_1, consensus_spectrum_intensity_mean_1))"
        );

        return query;
    }

    public static SolrQuery getBasicEDismaxQuery() {
        SolrQuery query = new SolrQuery();

        query.setParam("defType", QUERY_DEF_TYPE); // use DisMax query parser
        query.setParam("tie", EDISMAX_TIE_FACTOR); // 0.0 means that just the maximum scoring field is used. 1.0 means that all scoring fields are summed

        return query;
    }

}
