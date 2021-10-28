package fr.dademo.bi.companies.tools.batch.writer;


import org.springframework.batch.item.ItemWriter;

import javax.annotation.Nonnull;
import java.util.List;

public class NoActionItemWriter<T> implements ItemWriter<T> {

    @Override
    public void write(@Nonnull List<? extends T> items) {
        // Nothing to do
    }
}
