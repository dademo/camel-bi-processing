package fr.dademo.bi.companies.tools.batch.writer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jeasy.batch.core.record.Batch;
import org.jeasy.batch.core.record.Record;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BatchWriterTools {

    public static <T> Stream<Record<T>> recordsStreamOfBatch(Batch<T> batch) {
        return StreamSupport.stream(spliteratorOfBatchRecords(batch), false);
    }

    public static <T> Spliterator<Record<T>> spliteratorOfBatchRecords(Batch<T> batch) {

        return Spliterators.spliterator(
                batch.iterator(),
                batch.size(),
                Spliterator.DISTINCT |
                        Spliterator.SORTED |
                        Spliterator.SIZED |
                        Spliterator.NONNULL |
                        Spliterator.IMMUTABLE
        );
    }
}
