package fr.dademo.data.helpers.data_gouv_fr.repository.exception;

import fr.dademo.reader.http.repository.exception.BaseHttpQueryException;

import javax.annotation.Nonnull;

public class DataSetNotFoundException extends BaseHttpQueryException {

    private static final long serialVersionUID = -2396308059854053152L;

    public DataSetNotFoundException(@Nonnull String dataSetTitle, @Nonnull BaseHttpQueryException cause) {
        super(
                String.format("DataSet `%s` not found using query `%s`",
                        dataSetTitle, cause.getQueryResponse().request().url()),
                cause
        );
    }
}
