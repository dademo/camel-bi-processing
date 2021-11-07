package fr.dademo.bi.companies.repositories.file.identifier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.dademo.bi.companies.repositories.file.serializer.FileIdentifierDeserializer;
import fr.dademo.bi.companies.repositories.file.serializer.FileIdentifierSerializer;
import fr.dademo.bi.companies.repositories.file.validators.CachedFileValidator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.net.URL;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("java:S119")
@JsonSerialize(using = FileIdentifierSerializer.class)
@JsonDeserialize(using = FileIdentifierDeserializer.class)
@JsonIgnoreProperties({"validators"})
public interface FileIdentifier<SELF extends FileIdentifier<SELF>> extends Serializable {

    @Nonnull
    URL getBaseUrl();

    @Nonnull
    URL getQueryUrl();

    @Nullable
    String getFinalFileName();

    void setFinalFileName(@Nonnull String finalFileName);

    default List<CachedFileValidator<SELF>> getValidators() {
        return Collections.emptyList();
    }
}
