package fr.dademo.bi.companies.tools.batch.writer;

import org.jeasy.batch.core.record.Batch;
import org.jeasy.batch.core.writer.RecordWriter;

public final class NoActionBatchWriter<T> implements RecordWriter<T> {

    @Override
    public void writeRecords(Batch<T> batch) {
        // Nothing to do
    }
}
