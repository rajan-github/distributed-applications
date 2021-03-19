package model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Result implements Serializable {
    private Map<String, DocumentData> documentToDocumentData = new HashMap<>();

    public void addDocumentData(String document, DocumentData data) {
        documentToDocumentData.put(document, data);
    }

    public Map<String, DocumentData> getDocumentToDocumentData() {
        return Collections.unmodifiableMap(documentToDocumentData);
    }

}
