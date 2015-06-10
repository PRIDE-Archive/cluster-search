package uk.ac.ebi.pride.cluster.search.service;

import java.util.*;

/**
 * Default implementation of the synonym service
 *
 * @author Rui Wang
 * @version $Id$
 */
public class SynonymService implements ISynonymService {

    private final Map<String, Set<String>> synonymMapper = new HashMap<String, Set<String>>();

    public SynonymService(Map<String, Set<String>> synonyms) {
        this.synonymMapper.putAll(synonyms);
    }

    @Override
    public Set<String> getSynonyms(String accession) {
        Set<String> synonyms = synonymMapper.get(accession);
        return Collections.unmodifiableSet(synonyms);
    }

    @Override
    public Set<String> getSynonyms(Collection<String> accessions) {
        Set<String> synonyms = new HashSet<String>();

        for (String accession : accessions) {
            Set<String> mappedSynonyms = synonymMapper.get(accession);
            synonyms.addAll(mappedSynonyms);
        }

        return Collections.unmodifiableSet(synonyms);
    }
}
