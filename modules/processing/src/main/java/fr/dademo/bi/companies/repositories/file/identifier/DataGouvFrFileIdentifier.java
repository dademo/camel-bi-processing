package fr.dademo.bi.companies.repositories.file.identifier;

import java.time.LocalDateTime;

public interface DataGouvFrFileIdentifier extends FileIdentifier<DataGouvFrFileIdentifier> {

    String getDataSetName();

    LocalDateTime getCachedLastModified();
}
