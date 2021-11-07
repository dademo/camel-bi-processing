package fr.dademo.bi.companies.repositories.file.identifier;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;
import java.net.URL;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class BaseFileIdentifierImpl<T extends FileIdentifier<T>> implements FileIdentifier<T> {

    private static final long serialVersionUID = -155040342279375051L;

    @Nonnull
    private URL baseUrl;
    @Nonnull
    private URL queryUrl;
    private String finalFileName;

    public BaseFileIdentifierImpl(@Nonnull URL baseUrl) {
        this.baseUrl = baseUrl;
        this.queryUrl = baseUrl;
    }

    @Nonnull
    public URL getBaseUrl() {
        return baseUrl;
    }

    @Nonnull
    @Override
    public URL getQueryUrl() {
        return queryUrl;
    }

    public void setFinalFileName(@Nonnull String finalFileName) {
        this.finalFileName = finalFileName;
    }
}
