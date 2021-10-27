package fr.dademo.bi.companies.tools.batch.writer;

import fr.dademo.bi.companies.tools.batch.RecordWriterType;
import fr.dademo.bi.companies.tools.batch.writer.exceptions.WriterNotImplementedException;
import org.jeasy.batch.core.writer.RecordWriter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public abstract class RecordWriterProvider<T> {

    public final RecordWriter<T> getRecordWriter(@Nonnull RecordWriterType recordWriterType) {

        switch (recordWriterType) {
            case JDBC:
                return Optional.ofNullable(getJdbcRecordWriter())
                        .orElseThrow(WriterNotImplementedException.of(recordWriterType));
            case MONGODB:
                return Optional.ofNullable(getMongoDBRecordWriter())
                        .orElseThrow(WriterNotImplementedException.of(recordWriterType));
            case MESSAGING:
                return Optional.ofNullable(getMessagingRecordWriter())
                        .orElseThrow(WriterNotImplementedException.of(recordWriterType));
            case NO_ACTION:
                return Optional.ofNullable(getNoActionRecordWriter())
                        .orElseThrow(WriterNotImplementedException.of(recordWriterType));
            default:
                throw new WriterNotImplementedException(recordWriterType);
        }
    }

    @Nullable
    protected abstract JdbcRecordWriter<T> getJdbcRecordWriter();

    @Nullable
    protected abstract MongoDBRecordWriter<T> getMongoDBRecordWriter();

    @Nullable
    protected abstract MessagingRecordWriter<T> getMessagingRecordWriter();

    @Nullable
    protected NoActionBatchWriter<T> getNoActionRecordWriter() {
        return new NoActionBatchWriter<>();
    }
}
