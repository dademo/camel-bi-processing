package fr.dademo.bi.companies.repositories.file.identifier;

import fr.dademo.bi.companies.repositories.file.validators.CachedFileValidator;
import fr.dademo.bi.companies.repositories.file.validators.DataGouvFrValidator;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataGouvFrFileIdentifierImpl extends BaseFileIdentifierImpl<DataGouvFrFileIdentifier> implements DataGouvFrFileIdentifier {

    private static final long serialVersionUID = 5693165246495199848L;

    @Nonnull
    private String dataSetName;
    private LocalDateTime cachedLastModified;

    @Builder
    public DataGouvFrFileIdentifierImpl(@Nonnull URL baseUrl, @Nullable URL queryUrl, @Nonnull String dataSetName) {
        super(baseUrl, Optional.ofNullable(queryUrl).orElse(baseUrl));
        this.dataSetName = dataSetName;
    }

    @Override
    public List<CachedFileValidator<DataGouvFrFileIdentifier>> getValidators() {
        return Collections.singletonList(new DataGouvFrValidator());
    }
}
