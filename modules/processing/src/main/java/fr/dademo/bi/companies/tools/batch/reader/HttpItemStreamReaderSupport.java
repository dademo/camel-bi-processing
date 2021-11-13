package fr.dademo.bi.companies.tools.batch.reader;

import lombok.SneakyThrows;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import javax.annotation.Nonnull;
import java.io.InputStream;

public abstract class HttpItemStreamReaderSupport<T> implements ItemStreamReader<T> {

    @Override
    public void update(@Nonnull ExecutionContext executionContext) throws ItemStreamException {
        // Nothing to do
    }

    @SneakyThrows
    protected void sneakyClose(InputStream inputStream) {
        inputStream.close();
    }
}
