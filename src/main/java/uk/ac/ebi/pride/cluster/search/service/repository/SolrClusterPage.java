package uk.ac.ebi.pride.cluster.search.service.repository;

import org.apache.solr.common.SolrDocumentList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import uk.ac.ebi.pride.cluster.search.model.SolrCluster;

import java.util.Iterator;
import java.util.List;

/**
 * @author jadianes
 */
public class SolrClusterPage implements Page<SolrCluster> {

    private Pageable pageable;
    private SolrDocumentList solrDocumentList;
    private List<SolrCluster> clusters;

    public SolrClusterPage(Pageable pageable, SolrDocumentList solrDocumentList, List<SolrCluster> clusters) {
        this.pageable = pageable;
        this.solrDocumentList = solrDocumentList;
        this.clusters = clusters;
    }

    @Override
    public int getTotalPages() {
        return (int) (solrDocumentList.getNumFound() / pageable.getPageSize());
    }

    @Override
    public long getTotalElements() {
        return (int) solrDocumentList.getNumFound();
    }

    @Deprecated
    @Override
    public boolean hasPreviousPage() {
        return pageable.getPageNumber()>0;
    }

    @Deprecated
    @Override
    public boolean isFirstPage() {
        return pageable.getPageNumber()==0;
    }

    @Deprecated
    @Override
    public boolean hasNextPage() {
        return solrDocumentList.getStart()+pageable.getPageSize() < solrDocumentList.getNumFound();
    }

    @Deprecated
    @Override
    public boolean isLastPage() {
        return solrDocumentList.getStart()+pageable.getPageSize() > solrDocumentList.getNumFound();
    }

    @Override
    public int getNumber() {
        return pageable.getPageNumber();
    }

    @Override
    public int getSize() {
        return pageable.getPageSize();
    }

    @Override
    public int getNumberOfElements() {
        return clusters.size();
    }

    @Override
    public List<SolrCluster> getContent() {
        return clusters;
    }

    @Override
    public boolean hasContent() {
        return clusters!=null;
    }

    @Override
    public Sort getSort() {
        return null;
    }

    @Override
    public boolean isFirst() {
        return pageable.getPageNumber()==0;
    }

    @Override
    public boolean isLast() {
        return solrDocumentList.getStart()+pageable.getPageSize() > solrDocumentList.getNumFound();
    }

    @Override
    public boolean hasNext() {
        return solrDocumentList.getStart()+pageable.getPageSize() < solrDocumentList.getNumFound();
    }

    @Override
    public boolean hasPrevious() {
        return pageable.getPageNumber()>0;
    }

    @Override
    public Pageable nextPageable() {
        return null;
    }

    @Override
    public Pageable previousPageable() {
        return null;
    }

    @Override
    public Iterator<SolrCluster> iterator() {
        return clusters.iterator();
    }
}
