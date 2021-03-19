package model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Task implements Serializable {
    private final List<String> searchTerms;
    private final List<String> documents;

    public Task(List<String> _searchTerms, List<String> _documents) {
        this.searchTerms = _searchTerms;
        this.documents = _documents;
    }

    public List<String> getSearchTerms() {
        return Collections.unmodifiableList(searchTerms);
    }

    public List<String> getDocuments() {
        return Collections.unmodifiableList(documents);
    }
}
