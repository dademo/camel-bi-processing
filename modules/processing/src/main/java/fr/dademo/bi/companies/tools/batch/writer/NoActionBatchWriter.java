package fr.dademo.bi.companies.tools.batch.writer;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.batch.item.ItemWriter;

import javax.annotation.Nonnull;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NoActionBatchWriter<T> implements ItemWriter<T> {

    public static final NoActionBatchWriter<Object> INSTANCE = new NoActionBatchWriter<>();

    @Override
    public void write(@Nonnull List<? extends T> items) {
        // Nothing to do
    }
}
