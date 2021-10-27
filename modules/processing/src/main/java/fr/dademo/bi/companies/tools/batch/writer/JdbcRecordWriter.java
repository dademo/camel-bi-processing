package fr.dademo.bi.companies.tools.batch.writer;

import org.jeasy.batch.core.writer.RecordWriter;
import org.jooq.DSLContext;

public interface JdbcRecordWriter<T> extends RecordWriter<T> {

    DSLContext getDslContext();
}
