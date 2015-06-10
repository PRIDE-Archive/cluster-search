package uk.ac.ebi.pride.cluster.search.service;

import java.util.Collection;

/**
 * ISynonymService defines a set of interfaces to
 *
 * @author Rui Wang
 * @version $Id$
 */
public interface ISynonymService {

    /**
     * Get synonyms for a given accession
     *
     * @param accession an ontology accession
     * @return  a set of possible synonyms
     */
    Collection<String> getSynonyms(String accession);

    /**
     * Get synonyms for a set of accession
     *
     * @param accessions    a set of ontology accessions
     * @return  a set of possible synonyms
     */
    Collection<String> getSynonyms(Collection<String> accessions);
}
