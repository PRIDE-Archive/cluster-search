package uk.ac.ebi.pride.cluster.search.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Rui Wang
 * @version $Id$
 */
public class SynonymServiceFactory {

    public static final String COMMENT = "#";
    public static final String SYNONYM_SEPARATOR = "=>";
    public static final String TOKEN_SEPARATOR = ",";

    public static ISynonymService getInstanceFromInputStream(InputStream synonymFile) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(synonymFile));

        Map<String, Set<String>> synonyms = new HashMap<String, Set<String>>();

        String line;
        while((line = reader.readLine()) != null) {
            // ignore comment and empty lines
            if (!line.startsWith(COMMENT) && !line.trim().isEmpty()) {
                String[] parts = line.split(SYNONYM_SEPARATOR);
                if (parts.length == 2) {
                    String[] tokens = parts[0].trim().split(TOKEN_SEPARATOR);

                    for (String token : tokens) {
                        Set<String> syns = synonyms.get(token);
                        if (syns == null) {
                            syns = new HashSet<String>();
                            synonyms.put(token.trim(), syns);
                        }
                        syns.add(parts[1]);
                    }
                }
            }
        }

        return new SynonymService(synonyms);
    }
}
