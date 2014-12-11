package uk.ac.ebi.pride.cluster.search.service;

/**
 * @author ntoro
 * @author Jose A. Dianes
 * @version $Id$
 */

import org.apache.solr.SolrTestCaseJ4;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.SolrTemplate;
import uk.ac.ebi.pride.cluster.search.model.SolrCluster;
import uk.ac.ebi.pride.cluster.search.service.repository.SolrClusterRepositoryFactory;
import uk.ac.ebi.pride.cluster.search.service.repository.SolrClusterSpectralSearchRepository;
import uk.ac.ebi.pride.cluster.search.util.QualityAssigner;

import java.util.*;

public class ClusterIndexServiceTest extends SolrTestCaseJ4 {

    private static final long CLUSTER_ID = 1;
    private static final long NUM_SPECTRA = 100;
    private static final double MAX_RATIO = 0.7;
    private static final String PEP1 = "ABCDE";
    private static final String PEP2 = "FGHL";
    private static final String PROT1 = "P12345";
    private static final String PROT2 = "P67890";
    private static final String PROJECT1 = "PXT000001";
    private static final String P1_ASSAY1 = "01";
    private static final String P1_ASSAY2 = "02";
    private static final String PROJECT2 = "PXT000002";
    private static final String P2_ASSAY1 = "03";
    private static final String P2_ASSAY2 = "04";
    private static final double AVG_PRECURSOR_MZ = 800.34;
    private static final double AVG_PRECURSOR_CHARGE = 1.5;
    private static final double TEST_INTENSITY_MEAN_1 = 100.0;
    private static final double TEST_MZ_MEAN_1 = 1000.0;
    private ClusterIndexService clusterIndexService;
    private ClusterSearchService clusterSearchService;

    @BeforeClass
    public static void initialise() throws Exception {
        initCore("src/test/resources/solr/cluster-index/conf/solrconfig.xml",
                "src/test/resources/solr/cluster-index/conf/schema.xml",
                "src/test/resources/solr",
                "cluster-index");
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        SolrServer server = new EmbeddedSolrServer(h.getCoreContainer(), h.getCore().getName());
        SolrClusterRepositoryFactory solrClusterRepositoryFactory = new SolrClusterRepositoryFactory(new SolrTemplate(server));

        clusterIndexService = new ClusterIndexService(solrClusterRepositoryFactory.create());
        clusterSearchService = new ClusterSearchService(solrClusterRepositoryFactory.create(), new SolrClusterSpectralSearchRepository(server));

        // delete all data
        deleteAllData();

    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testSave() throws Exception {
        SolrCluster cluster = createCluster();
        clusterIndexService.save(cluster);

        checkCluster(clusterSearchService.findById(CLUSTER_ID));
    }

    @Test
    public void testFindByNearestPeaks() {
        SolrCluster cluster = createCluster();
        clusterIndexService.save(cluster);

        Page<SolrCluster> res = clusterSearchService.findByNearestPeaks(TEST_MZ_MEAN_1, TEST_INTENSITY_MEAN_1, new PageRequest(0, 1));
        assertNotNull(res);
        checkCluster( res.getContent().get(0) );
    }

    private void deleteAllData() {
        clusterIndexService.deleteAll();
    }

    private SolrCluster createCluster() {

        Set<String> pepSequences = new HashSet<String>();
        Collections.addAll(pepSequences, PEP1, PEP2);
        Set<String> proteinAccs = new HashSet<String>();
        Collections.addAll(proteinAccs, PROT1, PROT2);

        Map<String, List<String>> projectAssays = new HashMap<String, List<String>>();
        projectAssays.put(PROJECT1, new ArrayList<String>(Arrays.asList(P1_ASSAY1, P1_ASSAY2)));
        projectAssays.put(PROJECT2, new ArrayList<String>(Arrays.asList(P2_ASSAY1, P2_ASSAY2)));

        List<String> projects = new ArrayList<String>(Arrays.asList(PROJECT1, PROJECT2));

        SolrCluster cluster = new SolrCluster();
        cluster.setId(CLUSTER_ID);
        cluster.setClusterQuality(QualityAssigner.calculateQuality(NUM_SPECTRA, MAX_RATIO));
        cluster.setHighestRatioPepSequences(pepSequences);
        cluster.setHighestRatioProteinAccessions(proteinAccs);
        cluster.setMaxRatio(MAX_RATIO);
        cluster.setNumberOfSpectra(NUM_SPECTRA);
        cluster.setAveragePrecursorCharge(AVG_PRECURSOR_CHARGE);
        cluster.setAveragePrecursorMz(AVG_PRECURSOR_MZ);
        cluster.setProjects(projects);
        cluster.setProjectAssays(projectAssays);
        cluster.setConsensusSpectrumIntensityMean1(TEST_INTENSITY_MEAN_1);
        cluster.setConsensusSpectrumMzMean1(TEST_MZ_MEAN_1);
        return cluster;
    }

    private void checkCluster(SolrCluster cluster) {

        Set<String> pepSequences = new HashSet<String>();
        Collections.addAll(pepSequences, PEP1, PEP2);
        Set<String> proteinAccs = new HashSet<String>();
        Collections.addAll(proteinAccs, PROT1, PROT2);

        Map<String, List<String>> projectAssays = new HashMap<String, List<String>>();
        projectAssays.put(PROJECT1, new ArrayList<String>(Arrays.asList(P1_ASSAY1, P1_ASSAY2)));
        projectAssays.put(PROJECT2, new ArrayList<String>(Arrays.asList(P2_ASSAY1, P2_ASSAY2)));

        List<String> projects = new ArrayList<String>(Arrays.asList(PROJECT1, PROJECT2));

        assertNotNull(cluster);
        assertEquals(CLUSTER_ID, cluster.getId());
        assertEquals(MAX_RATIO, cluster.getMaxRatio(), 0);
        assertEquals(NUM_SPECTRA, cluster.getNumberOfSpectra());
        assertEquals(QualityAssigner.calculateQuality(NUM_SPECTRA, MAX_RATIO), cluster.getClusterQuality());
        assertEquals(pepSequences, cluster.getHighestRatioPepSequences());
        assertEquals(AVG_PRECURSOR_CHARGE, cluster.getAveragePrecursorCharge(), 0);
        assertEquals(AVG_PRECURSOR_MZ, cluster.getAveragePrecursorMz(), 0);
        assertEquals(proteinAccs, cluster.getHighestRatioProteinAccessions());
        assertEquals(projects, cluster.getProjects());

        for (String key : projectAssays.keySet()) {
            assertEquals(projectAssays.get(key), cluster.getProjectAssays().get(key));
        }
    }

}
