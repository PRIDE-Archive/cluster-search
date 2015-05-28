package uk.ac.ebi.pride.cluster.search.service;

/**
 * @author ntoro
 * @author Jose A. Dianes
 * @version $Id$
 */

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.pride.archive.dataprovider.identification.ModificationProvider;
import uk.ac.ebi.pride.cluster.search.model.ClusterFields;
import uk.ac.ebi.pride.cluster.search.model.SolrCluster;
import uk.ac.ebi.pride.cluster.search.service.repository.SolrClusterSpectralSearchRepository;
import uk.ac.ebi.pride.indexutils.modifications.Modification;
import uk.ac.ebi.pride.indexutils.results.PageWrapper;

import java.io.IOException;
import java.util.*;

import static junit.framework.Assert.*;
import static uk.ac.ebi.pride.cluster.search.service.repository.SolrClusterRepository.HIGHLIGHT_POST_FRAGMENT;
import static uk.ac.ebi.pride.cluster.search.service.repository.SolrClusterRepository.HIGHLIGHT_PRE_FRAGMENT;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class ClusterIndexServiceTest {

    private static final long CLUSTER_ID_1 = 1;
    private static final long CLUSTER_ID_2 = 2;
    private static final long CLUSTER_ID_3 = 3;
    private static final long CLUSTER_ID_4 = 4;
    private static final long NUM_SPECTRA = 100;
    private static final long TOTAL_NUM_SPECTRA = 110;

    private static final long NUM_PROJECTS = 5;
    private static final long TOTAL_NUM_PROJECTS = 6;

    private static final long NUM_SPECIES = 3;
    private static final long TOTAL_NUM_SPECIES = 4;

    private static final long NUM_MODIFICATIONS = 2;
    private static final long TOTAL_NUM_MODIFICATIONS = 12;

    //Modifications
    private static final Integer MOD_1_POS = 3;
    private static final Integer MOD_2_POS = 5;

    private static final String MOD_1_ACCESSION = "MOD:00696";
    private static final String MOD_2_ACCESSION = "MOD:00674";

    private static final String MOD_1_NAME = "phosphorylated residue";
    private static final String MOD_2_NAME = "amidated residue";

    private static final String MOD_1_SYNONYM = "phosphorylation";
    private static final String MOD_2_SYNONYM = "amidation";


    private static final double MAX_RATIO = 0.7;
    private static final String PEP1 = "ABCDEFG";
    private static final String PEP2 = "HIJKLMN";
    private static final String PROT1 = "P12345";
    private static final String PROT2 = "P67890";
    private static final String PROJECT1 = "PXT000001";
    private static final String P1_ASSAY1 = "01";
    private static final String P1_ASSAY2 = "02";
    private static final String PROJECT2 = "PXT000002";
    private static final String P2_ASSAY1 = "03";
    private static final String P2_ASSAY2 = "04";

    private static final double AVG_PRECURSOR_CHARGE = 1.5;

    private static final double AVG_PRECURSOR_MZ_1 = 2000.0;
    private static final double AVG_PRECURSOR_MZ_2 = 2500.0;
    private static final double AVG_PRECURSOR_MZ_3 = 3000.0;
    private static final double AVG_PRECURSOR_MZ_4 = 3500.0;

    private static final double TEST_INTENSITY_MEAN_1 = 1000.0;
    private static final double TEST_MZ_MEAN_1 = 100.0;


    private static final double TEST_INTENSITY_MEAN_2 = 2000.0;
    private static final double TEST_MZ_MEAN_2 = 200.0;


    private static final double TEST_INTENSITY_MEAN_3 = 3000.0;
    private static final double TEST_MZ_MEAN_3 = 300.0;


    private static final double TEST_INTENSITY_MEAN_4 = 4000.0;
    private static final double TEST_MZ_MEAN_4 = 400.0;

    private static final double TEST_INTENSITY_MEAN_5 = 5000.0;
    private static final double TEST_MZ_MEAN_5 = 500.0;

    private static final double TEST_INTENSITY_MEAN_6 = 6000.0;
    private static final double TEST_MZ_MEAN_6 = 600.0;

    private static final double TEST_INTENSITY_MEAN_7 = 7000.0;
    private static final double TEST_MZ_MEAN_7 = 700.0;

    @Autowired
    private SolrClusterSpectralSearchRepository solrClusterSpectralRepository;

    @Autowired
    SolrTemplate solrOperations;

    @Autowired
    ClusterSearchService clusterSearchService;

//    @BeforeClass
//    public static void initialise() throws Exception {
//        initCore("solr/cluster-index/conf/solrconfig.xml",
//                "solr/cluster-index/conf/schema.xml",
//                "solr",
//                "cluster-index");
//    }
//
//    @Before
//    @Override
//    public void setUp() throws Exception {
//        super.setUp();
//
//        server = new EmbeddedSolrServer(h.getCoreContainer(), h.getCore().getName());
//        solrTemplate = new SolrTemplate(server);
//
//        SolrClusterRepositoryFactory solrClusterRepositoryFactory = new SolrClusterRepositoryFactory(solrTemplate);
//        solrClusterSpectralRepository = new SolrClusterSpectralSearchRepository(server);
//        clusterSearchService = new ClusterSearchService(solrClusterRepositoryFactory.create(), solrClusterSpectralRepository);
//
//        // delete all data
//        deleteAllData();
//
//    }
//
//    @After
//    @Override
//    public void tearDown() throws Exception {
//        super.tearDown();
//    }


//
//    @Before
//    public void setUp() {
        // Create new repository instance using Factory and inject custom implementation
//        SolrClusterRepository repo = new SolrRepositoryFactory(this.solrOperations).getRepository(SolrClusterRepository.class,
//                new SolrClusterRepositoryImpl(this.solrOperations));
//        solrClusterSpectralRepository = new SolrClusterSpectralSearchRepository(serverFactory.getSolrServer());
//        clusterSearchService = new ClusterSearchService(repo, solrClusterSpectralRepository);

//    }

    @After
    public void tearDown() {
        solrOperations.delete(new SimpleQuery(new SimpleStringCriteria("*:*")));
        solrOperations.commit();
    }

    @Test
    public void testFindByTextAndHighestRatioPepSequencesHighlightsOnModificationNames() throws IOException, SolrServerException {


        SolrCluster cluster1 = createCluster(CLUSTER_ID_1, AVG_PRECURSOR_MZ_1, createMzValuesSet1(), createIntensityValuesSet1());
        save(cluster1);

        PageWrapper<SolrCluster> highlightPage =
                clusterSearchService.findByTextAndHighestRatioPepSequencesHighlightsFilterOnModificationNamesAndSpeciesNames(
                        PEP1, null, null, null, null, new PageRequest(0, 10));

        assertNotNull(highlightPage);
        assertEquals(1, highlightPage.getHighlights().size());
        assertEquals(ClusterFields.HIGHEST_RATIO_PEP_SEQUENCE, highlightPage.getHighlights().entrySet().iterator().next().getValue().entrySet().iterator().next().getKey());
        assertEquals(HIGHLIGHT_PRE_FRAGMENT + PEP1 + HIGHLIGHT_POST_FRAGMENT, highlightPage.getHighlights().entrySet().iterator().next().getValue().entrySet().iterator().next().getValue().get(0));
    }

    @Test
    public void testFindByTextAndHighestRatioPepSequencesFacetOnModificationNames() throws IOException, SolrServerException {


        SolrCluster cluster1 = createCluster(CLUSTER_ID_1, AVG_PRECURSOR_MZ_1, createMzValuesSet1(), createIntensityValuesSet1());
        save(cluster1);

        Map<String, Map<String, Long>> modificationsCount = clusterSearchService.findByTextAndHighestRatioPepSequencesFacetOnModificationNamesAndSpeciesNames(
                PEP1, null, null, null);

        assertNotNull(modificationsCount);
        assertEquals(2, modificationsCount.get(ClusterFields.MOD_SYNONYMS).size());
        assertEquals((Long) 1L, modificationsCount.get(ClusterFields.MOD_SYNONYMS).get(MOD_1_SYNONYM));
        assertEquals((Long) 1L, modificationsCount.get(ClusterFields.MOD_SYNONYMS).get(MOD_2_SYNONYM));

    }


    @Test
    public void testFindByNearestPeaks() throws IOException, SolrServerException {
        SolrCluster cluster3 = createCluster(CLUSTER_ID_3, AVG_PRECURSOR_MZ_3, createMzValuesSet3(), createIntensityValuesSet3());
        save(cluster3);
        SolrCluster cluster2 = createCluster(CLUSTER_ID_2, AVG_PRECURSOR_MZ_2, createMzValuesSet2(), createIntensityValuesSet2());
        save(cluster2);
        SolrCluster cluster1 = createCluster(CLUSTER_ID_1, AVG_PRECURSOR_MZ_1, createMzValuesSet1(), createIntensityValuesSet1());
        save(cluster1);
        SolrCluster cluster4 = createCluster(CLUSTER_ID_4, AVG_PRECURSOR_MZ_4, createMzValuesSet4(), createIntensityValuesSet4());
        save(cluster4);

        Page<SolrCluster> res = solrClusterSpectralRepository.findByNearestPeaks("HIGH", AVG_PRECURSOR_MZ_1, 100.0, createMzValuesSetUser(), createIntensityValuesSetUser(), new PageRequest(0, 5));
        assertNotNull(res);
        assertTrue(res.getTotalElements() == 4);
        checkCluster(CLUSTER_ID_1, AVG_PRECURSOR_MZ_1, res.getContent().get(0) );
        checkCluster(CLUSTER_ID_2, AVG_PRECURSOR_MZ_2, res.getContent().get(1) );
        checkCluster(CLUSTER_ID_3, AVG_PRECURSOR_MZ_3, res.getContent().get(2) );
        checkCluster(CLUSTER_ID_4, AVG_PRECURSOR_MZ_4, res.getContent().get(3) );
    }

    private void save(SolrCluster cluster) throws IOException, SolrServerException {
        solrOperations.saveBean(cluster);
        solrOperations.commit();
//        server.addBean(cluster);
//        server.commit();
    }

//    private void deleteAllData() throws IOException, SolrServerException {
//        server.deleteByQuery("*:*");
//    }

    private SolrCluster createCluster(long clusterId, double precursorMz, double[] mzValues, double[] intensityValues) {

        List<String> proteinAccs = new LinkedList<String>();
        Collections.addAll(proteinAccs, PROT1, PROT2);

        Map<String, List<String>> projectAssays = new HashMap<String, List<String>>();
        projectAssays.put(PROJECT1, new ArrayList<String>(Arrays.asList(P1_ASSAY1, P1_ASSAY2)));
        projectAssays.put(PROJECT2, new ArrayList<String>(Arrays.asList(P2_ASSAY1, P2_ASSAY2)));

        List<String> projects = new ArrayList<String>(Arrays.asList(PROJECT1, PROJECT2));

        SolrCluster cluster = new SolrCluster();
        cluster.setId(clusterId);
        cluster.setClusterQuality("HIGH");
        cluster.setHighestRatioPepSequence(PEP1);
        cluster.setHighestRatioProteinAccessions(proteinAccs);

        Modification mod1 = new Modification();
        mod1.addPosition(MOD_1_POS, null);
        mod1.setAccession(MOD_1_ACCESSION);
        mod1.setName(MOD_1_NAME);

        Modification mod2 = new Modification();
        mod2.addPosition(MOD_2_POS, null);
        mod2.setAccession(MOD_2_ACCESSION);
        mod2.setName(MOD_2_NAME);

        List<ModificationProvider> modifications = new LinkedList<ModificationProvider>();
        modifications.add(mod1);
        modifications.add(mod2);

        cluster.setModifications(modifications);

        cluster.setMaxRatio(MAX_RATIO);
        cluster.setNumberOfSpectra(NUM_SPECTRA);
        cluster.setTotalNumberOfSpectra(TOTAL_NUM_SPECTRA);
        cluster.setNumberOfProjects(NUM_PROJECTS);
        cluster.setTotalNumberOfProjects(TOTAL_NUM_PROJECTS);
        cluster.setNumberOfSpecies(NUM_SPECIES);
        cluster.setTotalNumberOfSpecies(TOTAL_NUM_SPECIES);
        cluster.setNumberOfModifications(NUM_MODIFICATIONS);
        cluster.setTotalNumberOfModifications(TOTAL_NUM_MODIFICATIONS);
        cluster.setAveragePrecursorCharge(AVG_PRECURSOR_CHARGE);
        cluster.setAveragePrecursorMz(precursorMz);
        cluster.setProjects(projects);
        cluster.setProjectAssays(projectAssays);
        cluster.setConsensusSpectrumMzMeans(mzValues);
        cluster.setConsensusSpectrumIntensityMeans(intensityValues);
        return cluster;
    }

    private void checkCluster(long clusterId, double avgPrecursorMz,SolrCluster cluster) {

        List<String> proteinAccs = new LinkedList<String>();
        Collections.addAll(proteinAccs, PROT1, PROT2);

        Map<String, List<String>> projectAssays = new HashMap<String, List<String>>();
        projectAssays.put(PROJECT1, new ArrayList<String>(Arrays.asList(P1_ASSAY1, P1_ASSAY2)));
        projectAssays.put(PROJECT2, new ArrayList<String>(Arrays.asList(P2_ASSAY1, P2_ASSAY2)));

        List<String> projects = new ArrayList<String>(Arrays.asList(PROJECT1, PROJECT2));

        assertNotNull(cluster);
        assertEquals(clusterId, cluster.getId());
        assertEquals(MAX_RATIO, cluster.getMaxRatio(), 0);
        assertEquals(NUM_SPECTRA, cluster.getNumberOfSpectra());
        assertEquals("HIGH", cluster.getClusterQuality());
        assertEquals(PEP1, cluster.getHighestRatioPepSequence());
        assertEquals(AVG_PRECURSOR_CHARGE, cluster.getAveragePrecursorCharge(), 0);
        assertEquals(avgPrecursorMz, cluster.getAveragePrecursorMz(), 0);
        assertEquals(proteinAccs, cluster.getHighestRatioProteinAccessions());
        assertEquals(projects, cluster.getProjects());

        for (String key : projectAssays.keySet()) {
            assertEquals(projectAssays.get(key), cluster.getProjectAssays().get(key));
        }
    }

    private double[] createMzValuesSet1() {
        double[] res = new double[4];
        res[0] = TEST_MZ_MEAN_1;
        res[1] = TEST_MZ_MEAN_2;
        res[2] = TEST_MZ_MEAN_3;
        res[3] = TEST_MZ_MEAN_4;
        return res;
    }

    private double[] createIntensityValuesSet1() {
        double[] res = new double[4];
        res[0] = TEST_INTENSITY_MEAN_1;
        res[1] = TEST_INTENSITY_MEAN_2;
        res[2] = TEST_INTENSITY_MEAN_3;
        res[3] = TEST_INTENSITY_MEAN_4;
        return res;
    }

    private double[] createMzValuesSet2() {
        double[] res = new double[4];
        res[0] = TEST_MZ_MEAN_2;
        res[1] = TEST_MZ_MEAN_3;
        res[2] = TEST_MZ_MEAN_4;
        res[3] = TEST_MZ_MEAN_5;
        return res;
    }

    private double[] createIntensityValuesSet2() {
        double[] res = new double[4];
        res[0] = TEST_INTENSITY_MEAN_2;
        res[1] = TEST_INTENSITY_MEAN_3;
        res[2] = TEST_INTENSITY_MEAN_4;
        res[3] = TEST_INTENSITY_MEAN_5;
        return res;
    }

    private double[] createMzValuesSet3() {
        double[] res = new double[4];
        res[0] = TEST_MZ_MEAN_3;
        res[1] = TEST_MZ_MEAN_4;
        res[2] = TEST_MZ_MEAN_5;
        res[3] = TEST_MZ_MEAN_6;
        return res;
    }

    private double[] createIntensityValuesSet3() {
        double[] res = new double[4];
        res[0] = TEST_INTENSITY_MEAN_3;
        res[1] = TEST_INTENSITY_MEAN_4;
        res[2] = TEST_INTENSITY_MEAN_5;
        res[3] = TEST_INTENSITY_MEAN_6;
        return res;
    }

    private double[] createMzValuesSet4() {
        double[] res = new double[4];
        res[0] = TEST_MZ_MEAN_4;
        res[1] = TEST_MZ_MEAN_5;
        res[2] = TEST_MZ_MEAN_6;
        res[3] = TEST_MZ_MEAN_7;
        return res;
    }

    private double[] createIntensityValuesSet4() {
        double[] res = new double[4];
        res[0] = TEST_INTENSITY_MEAN_4;
        res[1] = TEST_INTENSITY_MEAN_5;
        res[2] = TEST_INTENSITY_MEAN_6;
        res[3] = TEST_INTENSITY_MEAN_7;
        return res;
    }

    private double[] createMzValuesSetUser() {
        double[] res = new double[4];
        res[0] = TEST_MZ_MEAN_1 + (Math.random() * 40.0);
        res[1] = TEST_MZ_MEAN_2 + (Math.random() * 40.0);
        res[2] = TEST_MZ_MEAN_3 + (Math.random() * 40.0);
        res[3] = TEST_MZ_MEAN_4 + (Math.random() * 40.0);
        return res;
    }

    private double[] createIntensityValuesSetUser() {
        double[] res = new double[4];
        res[0] = TEST_INTENSITY_MEAN_1 + (Math.random() * 400.0);
        res[1] = TEST_INTENSITY_MEAN_2 + (Math.random() * 400.0);
        res[2] = TEST_INTENSITY_MEAN_3 + (Math.random() * 400.0);
        res[3] = TEST_INTENSITY_MEAN_4 + (Math.random() * 400.0);
        return res;
    }

}
